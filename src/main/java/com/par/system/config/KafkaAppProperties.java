package com.par.system.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * 
 * @author harshbhavsar
 *
 */
@Component
@ConfigurationProperties(prefix = "app.properties.kafka")
public class KafkaAppProperties implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String topic;

	private String messageKey;

	private String zookeeperAddress;
	
	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getMessageKey() {
		return this.messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getZookeeperAddress() {
		return zookeeperAddress;
	}

	public void setZookeeperAddress(String zookeeperAddress) {
		this.zookeeperAddress = zookeeperAddress;
	}
	
}
