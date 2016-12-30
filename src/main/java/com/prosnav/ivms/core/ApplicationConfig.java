
package com.prosnav.ivms.core;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * @author wangnan
 *
 */
@Configuration
@EnableScheduling
@ImportResource("classpath:spring/ApplicationContext.xml")
public class ApplicationConfig {
	
	@Autowired
	private Environment env;
	
	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		MongoCredential credential = MongoCredential.createCredential(env.getProperty("spring.data.mongodb.username"), env.getProperty("spring.data.mongodb.database"), env.getProperty("spring.data.mongodb.password").toCharArray());
		ServerAddress serverAddress = new ServerAddress(env.getProperty("spring.data.mongodb.host"));
		MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential)); 
	    return new SimpleMongoDbFactory(mongoClient, env.getProperty("spring.data.mongodb.database"));
    }

	public @Bean MongoTemplate mongoTemplate() throws Exception {
	    return new MongoTemplate(mongoDbFactory());
	}
}
