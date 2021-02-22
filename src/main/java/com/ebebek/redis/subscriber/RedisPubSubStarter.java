package com.ebebek.redis.subscriber;

import com.ebebek.redis.subscriber.config.PubSubConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RedisPubSubStarter {
    public static void main(String[] args) {
            new AnnotationConfigApplicationContext(PubSubConfig.class);
    }
}