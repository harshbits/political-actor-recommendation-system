package com.par.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.par.system.config.ApplicationProperties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@RequestMapping(value = { "/", "" })
	public String index() {
		
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
		Jedis jedis = pool.getResource();
		String ans  = jedis.get("psa.actor.new-actor");
		return ans;
	}
	
	
	/**
	 * If something went wrong during the entire process.
	 * 
	 * @param e
	 * @return
	 */
	@RequestMapping(value = { "/error", "" })
	@ExceptionHandler(Exception.class)
	public String hadnleException(Exception e) {
		e.printStackTrace();
		return "error";
	}

}
