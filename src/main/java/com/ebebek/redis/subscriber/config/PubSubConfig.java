package com.ebebek.redis.subscriber.config;

import com.ebebek.redis.subscriber.service.SubscribeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
public class PubSubConfig {
    private final RedisConnectionFactory factory;
    private final RedisTemplate<String, Object> template;
    @Autowired
    private ChannelTopic topic;
    private MessageListenerAdapter adapter;

    public PubSubConfig(RedisConnectionFactory factory, RedisTemplate template) {
        this.factory = factory;
        this.template = template;
        this.adapter = new MessageListenerAdapter(new SubscribeListener(template));
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(adapter, topic);
        return container;
    }

}
