package com.ebebek.redis.subscriber.service;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicLong;

public class RedisSubscriber {
    private final RedisTemplate<String, String> template;
    private final MessageListener listener;
    private final AtomicLong counter = new AtomicLong(0);

    public RedisSubscriber(final RedisTemplate<String, String> template,
                           final MessageListener listener) {
        this.template = template;
        this.listener = listener;
    }

    @Scheduled(fixedDelay = 1000)
    public void subscribe() {
        template.getConnectionFactory().getClusterConnection().subscribe(listener);
        template.convertAndSend(topic.getTopic(), "Message " + counter.incrementAndGet() +
                ", " + Thread.currentThread().getName());
    }
}