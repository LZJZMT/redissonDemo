package com.lzj.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: dell
 * @Date： 2019/12/23 16:29
 */

@Slf4j
@Controller
public class RedissonController {

    @Autowired
    RedissonClient redissonClient;

    @ResponseBody
    @RequestMapping("/lock1")
    public LocalDateTime lock1() throws InterruptedException {
        RLock lock1 = redissonClient.getLock("lock1");
        log.warn("lock1 lock now!");
        lock1.lock(10, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(1*60);
        log.warn("lock1 unlock now!");
        return LocalDateTime.now();
    }

    @ResponseBody
    @RequestMapping("/lock2")
    public LocalDateTime lock2(){
        RLock lock1 = redissonClient.getLock("lock1");
        log.warn("lock2 lock now!");
        boolean locked = lock1.isLocked();
        lock1.lock();

        log.warn("lock2 unlock now!");
        return LocalDateTime.now();
    }
}
