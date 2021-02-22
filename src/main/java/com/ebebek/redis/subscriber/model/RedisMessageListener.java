//package com.ebebek.redis.subscriber.model;
//
//import com.ebebek.redis.subscriber.service.SubscribeListener;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//
//@Slf4j
//public class RedisMessageListener implements MessageListener {
//
//    @Autowired
//    private SubscribeListener subscribeService;
//
//    @Override
//    public void onMessage(final Message message, final byte[] bytes) {
//        log.info("Redis Subscriber Received Message : {}", message.toString());
//
//    }
//}
