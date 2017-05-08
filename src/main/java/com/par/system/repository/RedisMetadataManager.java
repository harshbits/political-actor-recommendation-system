package com.par.system.repository;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.par.system.config.ApplicationProperties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisMetadataManager implements Serializable {

	private static final long serialVersionUID = 1L;

	// @Autowired
	// private static RedisTemplate<String, String> redisTemplate;

	// @Autowired
	// private RedisConnection redisConnection;

	@Autowired
	private ApplicationProperties applicationProperties;

	public boolean validate(String actor) {

		try {
			// String response = (String)
			// redisTemplate.opsForHash().get(applicationProperties.getRedisCameoKey(),
			// actor);

			// System.out.println(response);

			// String response = redisConnection
			// .hGet(applicationProperties.getRedisCameoKey().getBytes(),
			// actor.getBytes()).toString();

			// System.out.println(response);

			// return
			// redisTemplate.opsForHash().hasKey(applicationProperties.getRedisCameoKey(),
			// actor);

//			JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
//			Jedis jedis = pool.getResource();
//			jedis.select(0);
////			Jedis jedis = new Jedis("localhost", 6379);
//			String response = jedis.hget(applicationProperties.getRedisCameoKey(), actor).toString();
//			System.out.println(response);
//			return jedis.hexists(applicationProperties.getRedisCameoKey(), actor);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;

	}

}
