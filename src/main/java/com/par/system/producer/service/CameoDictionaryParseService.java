package com.par.system.producer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.gson.Gson;
import com.par.system.beans.DirectoryActor;
import com.par.system.config.ApplicationProperties;

public class CameoDictionaryParseService {

	private static final String CAMEO_ACTOR_DIC_PATH = "classpath:dictionaries/Phoenix.US.actors.txt";

	private static final Logger logger = LoggerFactory.getLogger(CameoDictionaryParseService.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private ApplicationProperties applicationProperties;

	private static DirectoryActor bean = new DirectoryActor();

	private static List<String> alias = new ArrayList<>();

	private static List<String> roles = new ArrayList<>();

	public void storeActors() {

		try {
			Resource resource = new FileSystemResourceLoader().getResource(CAMEO_ACTOR_DIC_PATH);
			InputStream dbAsStream = resource.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(dbAsStream));
			reader.lines().forEach(line -> parseActors(line));

		} catch (IOException e) {
			logger.error("{}", e);
		}
	}

	private void parseActors(String line) {
		if (!line.trim().startsWith("#")) {
			if (!line.startsWith("+") && !line.startsWith("[")) {
				String str = line.split(" ")[0].trim();
				if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '_') {
					str = str.substring(0, str.length() - 1);
				}
				if (bean.getKey() != null) {
					storeToRedis(bean);
				}
				alias.clear();
				roles.clear();
				bean.setKey(str);

			} else {
				if (line.startsWith("+")) {
					if (bean.getKey() != null) {
						String str = line.split(" ")[0].trim().substring(1);
						if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '_') {
							str = str.substring(0, str.length() - 1);
						}
						alias.add(str);
					}
				} else if (line.startsWith("[") && line.endsWith("]")) {
					if (bean.getKey() != null) {
						roles.add(line);
					}
				}
			}
			bean.setAlias(alias);
			bean.setRoles(roles);
		}
	}

	private void storeToRedis(DirectoryActor bean) {
		List<DirectoryActor> beanList = new ArrayList<>();
		for (String alias : bean.getAlias()) {
			DirectoryActor storeBean = new DirectoryActor();
			List<String> aList = new ArrayList<>();
			aList = bean.getAlias().stream().filter(a -> !a.equals(alias)).collect(Collectors.toList());
			aList.add(bean.getKey());
			storeBean.setKey(alias);
			storeBean.setAlias(aList);
			storeBean.setRoles(bean.getRoles());
			beanList.add(storeBean);
			logger.info(new Gson().toJson(storeBean));

			redisTemplate.opsForHash().put(applicationProperties.getRedisCameoKey(), storeBean.getKey(), new Gson().toJson(storeBean));
		}
	}

}
