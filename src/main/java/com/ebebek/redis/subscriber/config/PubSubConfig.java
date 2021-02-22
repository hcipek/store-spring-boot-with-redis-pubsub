package com.ebebek.redis.subscriber.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class PubSubConfig {

    private final RedisConnectionFactory factory;

    private ChannelTopic topic;
    private MessageListenerAdapter adapter;

    @Autowired
    private RedisTemplate redisTemplate;

    public PubSubConfig(RedisConnectionFactory factory) {
        this.factory = factory;
        this.topic = new
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(factory);
        container.addMessageListener(adapter, topic);

        return container;
    }

}
