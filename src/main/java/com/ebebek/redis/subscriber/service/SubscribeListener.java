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

    private ListOperations<String, String> listOperations;

    public SubscribeListener(RedisTemplate redisTemplate) {
        this.listOperations = redisTemplate.opsForList();
    }

    @Override
    public void onMessage(final Message message, final byte[] bytes) {
        log.info("Redis Subscriber Received Message : {}", message.toString());
        rightPushMessage(message.toString());
    }

    private void rightPushMessage(String message) {
        listOperations.rightPush("RMS", message);
    }

    public String getNext() {
        return listOperations.leftPop("RMS");
    }

    public List<String> getWithSize(int size) {
        List<String> items = new ArrayList<>();
        for(int i=0; i<size; i++)
            items.add(listOperations.leftPop("RMS"));
        return items;
    }
}