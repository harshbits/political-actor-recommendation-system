package com.par.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.par.system.config.ApplicationProperties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Controller
@RequestMapping("/ans")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, produces ="application/json")
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
