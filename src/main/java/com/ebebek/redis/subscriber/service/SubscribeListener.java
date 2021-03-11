package com.ebebek.redis.subscriber.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SubscribeListener implements MessageListener {

    private RedisTemplate redisTemplate;
    private ListOperations<String, String> listOperations;

    private static final String REDIS_MESSAGE_KEY = "Messages";

    public SubscribeListener(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOperations = redisTemplate.opsForList();
    }

    @Override
    public void onMessage(final Message message, final byte[] bytes) {
        log.info("Redis Subscriber Received Message : {}", message.toString());
        sendMessage("I got it bro!, " + message.toString());
        rightPushMessage(message.toString());
    }

    private void rightPushMessage(String message) {
        listOperations.rightPush(REDIS_MESSAGE_KEY, message);
    }

    public String getNext() {
        return listOperations.leftPop(REDIS_MESSAGE_KEY);
    }

    public List<String> getWithSize(int size) {
        List<String> items = new ArrayList<>();
        for(int i=0; i<size; i++) {
            String item = listOperations.leftPop(REDIS_MESSAGE_KEY);
            if(item == null)
                break;
            items.add(item);
        }
        return items;
    }

    public Long getSize() {
        return listOperations.size(REDIS_MESSAGE_KEY);
    }

    public void sendMessage(String message) {
        redisTemplate.convertAndSend("pubsub:info-reply", message);
    }
}