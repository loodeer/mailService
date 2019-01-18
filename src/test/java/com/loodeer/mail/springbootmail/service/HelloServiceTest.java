package com.loodeer.mail.springbootmail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author loodeer
 * @date 2019-01-18 22:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloServiceTest {

    @Resource
    HelloService helloService;

    @Test
    public void sayHelloTest() {
        helloService.sayHello();
    }
}
