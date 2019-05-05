package com.catdog.jd.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.web.filter.DelegatingFilterProxy;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Properties;


@Configuration
public class RedisConfig {

	private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(RedisConfig.class);

	@Value(value = "${redis.host}")
	private String redisHost;
	@Value(value = "${redis.port}")
	private String redisPort;
	@Value(value = "${redis.index}")
	private String redisIndex;

	@Bean(value = "systemProperties")
	public Properties getProperties(){
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/system.properties"));
		try {
			propertiesFactoryBean.afterPropertiesSet();
			return propertiesFactoryBean.getObject();
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("加载失败");
		}
		return null;
	}

	@Bean
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(100);
		jedisPoolConfig.setMaxIdle(20);
		jedisPoolConfig.setMinIdle(10);
		jedisPoolConfig.setMaxWaitMillis(10000);
		jedisPoolConfig.setTestWhileIdle(true);
		return jedisPoolConfig;
	}
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig,Properties properties){
		LOGGER.info("=======================redis数据库==={}",redisIndex);
		//单机模式
		RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
		rsc.setHostName(properties.getProperty("redis.host").trim());
		rsc.setPort(Integer.parseInt(properties.getProperty("redis.port","6379").trim()));
		rsc.setDatabase(Integer.parseInt(properties.getProperty("redis.index","1").trim()));
//		rsc.setDatabase(1);
//		rsc.setPassword(RedisPassword.of(""));
		//添加链接池
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jcjb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
		jcjb.poolConfig(jedisPoolConfig);
		JedisClientConfiguration jedisClientConfiguration =jcjb.build();
		return new JedisConnectionFactory(rsc,jedisClientConfiguration);
	}
	@Bean
	public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory){

		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);


		// 使用Jackson2JsonRedisSerialize 替换默认序列化
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		// 设置String类型的序列化规则和 key的序列化规则
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		// 设置Hash类型的序列化规则和 key的序列化规则
		redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

		redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setEnableDefaultSerializer(true);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

//	@Bean
//	public DelegatingFilterProxy delegatingFilterProxy(){
//		return new DelegatingFilterProxy();
//	}

//	@Bean
//	public RedisHttpSessionConfiguration redisHttpSessionConfiguration(){
//		RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
//		redisHttpSessionConfiguration.setMaxInactiveIntervalInSeconds(30*1000);
//		return redisHttpSessionConfiguration;
//	}
}
