package com.ebebek.redis.subscriber.controller;

import com.ebebek.redis.subscriber.service.SubscribeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rms")
public class RMSController {

    @Autowired
    private SubscribeListener listener;

    @GetMapping("/getNext")
    public String getNext() {
        return listener.getNext();
    }

    @GetMapping("/getWithSize")
    public List<String> getWithSize(@RequestParam("size") int size) {
        return listener.getWithSize(size);
    }
}
