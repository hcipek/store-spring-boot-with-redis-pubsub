package com.ebebek.redis.subscriber;

import com.ebebek.redis.subscriber.service.SubscribeListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SubscriberApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscriberApplication.class, args);
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter( new SubscribeListener(new RedisTemplate()) );
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic( "pubsub:queue" );
    }

    @Bean
    public RestTemplate getRestTemplate() { return new RestTemplate(); }
}
