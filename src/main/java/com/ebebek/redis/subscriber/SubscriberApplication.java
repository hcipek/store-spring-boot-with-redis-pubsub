package com.ebebek.redis.subscriber;

import com.ebebek.redis.subscriber.model.RedisMessageListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
public class SubscriberApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscriberApplication.class, args);
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter( new RedisMessageListener() );
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic( "pubsub:queue" );
    }
}
