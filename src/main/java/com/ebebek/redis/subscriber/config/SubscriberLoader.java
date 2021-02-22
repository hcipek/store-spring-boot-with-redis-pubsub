package com.ebebek.redis.subscriber.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SubscriberLoader {
    private final RedisConnectionFactory factory;

    private RedisTemplate redisTemplate;

    public SubscriberLoader(RedisConnectionFactory factory) {
        this.factory = factory;
    }

    @PostConstruct
    public void connectionSetup() {
        RedisClient client = RedisClient.create(redisURIBuilder());
        StatefulRedisConnection<String, String> statefulRedisConnection = client.connect();
    }

    private RedisURI redisURIBuilder() {
        RedisURI uri = RedisURI.Builder
                .redis("localhost", 6379)
//                .withAuthentication("username","password")
                .withDatabase(1)
                .build();
        return uri;
    }

    @Bean
    @Primary
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        final RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setValueSerializer(serializerSetup());
//        template.setHashValueSerializer();
//        template.setKeySerializer();
//        template.setHashKeySerializer();
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        final StringRedisTemplate template = new StringRedisTemplate(factory);
        template.setValueSerializer(serializerSetup());
        template.afterPropertiesSet();

        return template;
    }

    private Jackson2JsonRedisSerializer serializerSetup() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }
}
