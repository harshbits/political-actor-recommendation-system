package com.par.system.producer.service;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

public class CameoDictionaryParseService {

	private static final String CAMEO_ACTOR_DIC_PATH = "classpath:dictionaries/Phoenix.Countries.actors.txt";
	
	private static final Logger logger = LoggerFactory.getLogger(CameoDictionaryParseService.class);

	public void storeActors() {

		try {
			Resource resource = new FileSystemResourceLoader().getResource(CAMEO_ACTOR_DIC_PATH);
			InputStream dbAsStream = resource.getInputStream();
			
			
			
		} catch (IOException e) {
			logger.error(""+e.getStackTrace());
		}
	}

}
