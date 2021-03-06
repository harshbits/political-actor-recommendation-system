package com.par.system.consumer.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.par.system.beans.ActorGroupFrequency;
import com.par.system.beans.PoliticalNewsData;
import com.par.system.config.ApplicationProperties;
import com.par.system.config.KafkaAppProperties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Scope("prototype")
@Service
public class ParConsumerService implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ParConsumerService.class);

	@Autowired
	private KafkaAppProperties kafkaProperties;

	@Autowired
	private PerformNERJaccardService performNERJaccardService;

	@Autowired
	private NewActorDiscoveryService newActorDiscoveryService;

	@Autowired
	private ApplicationProperties applicationProperties;

	int count = 0;

	public void runConsumer() throws InterruptedException {

		SparkConf sparkConf = new SparkConf().setMaster("local[4]").setAppName("PARScrapperConsumer");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);
		JavaStreamingContext streamingContext = new JavaStreamingContext(sc, Durations.seconds(1));

		Map<String, Object> kafkaParams = new HashMap<>();

		kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, "politicalActorRecommendation");
		kafkaParams.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		kafkaParams.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

		Collection<String> topics = Arrays.asList(kafkaProperties.getTopic());

		JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(streamingContext,
				LocationStrategies.PreferConsistent(),
				ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

		JavaDStream<String> lines = stream.map(x -> x.value().toString());

		JavaDStream<PoliticalNewsData> politicsNews = lines.map(x -> new Gson().fromJson(x, PoliticalNewsData.class));

		JavaDStream<ActorGroupFrequency> actorGroups = politicsNews
				.map(x -> performNERJaccardService.getActorGroupFrequency(x));

		actorGroups.foreachRDD((VoidFunction<JavaRDD<ActorGroupFrequency>>) rdd -> {
			rdd.foreach((VoidFunction<ActorGroupFrequency>) s -> {

				// jedis.select(0);
				if (count < 1) {
					for (String actor : s.getHighestOccuredActorMap().keySet()) {
						
						logger.info("Actor Group : "+s.getHighestOccuredActorMap());
						try {
							JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
							Jedis jedis = pool.getResource();
							logger.info("Input Actor Check: " + formatActor(actor));
							Map<String, Boolean> ans = new HashMap();
							// System.out.println(jedis.hexists("psa.actor.dictionary",
							// formatActor(actor)));
							if (jedis.hexists("psa.actor.dictionary", formatActor(actor))) {
								ans.put(formatActor(actor), true);
							} else {
								ans.put(formatActor(actor), false);
							}

//							jedis.set("psa.actor.new-actor", new Gson().toJson(ans));
							
							logger.info(new Gson().toJson(ans));

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					count = count + 1;
				}
			});
		});
		// JavaDStream<String> actorString = actorGroups.map(x -> new
		// Gson().toJson(newActorDiscoveryService.getNewActor(x)));

		// actorString.print();

		try {
			streamingContext.start();
			logger.info("PAR Consumer Started !!");
			streamingContext.awaitTermination();

		} catch (Exception e) {
			logger.error("Error {}", e);
		}

	}

	private String formatActor(String actor) {
		String result = actor.trim().replace(" ", "_");
		result = result.toUpperCase().trim();
		return result;
	}

}
