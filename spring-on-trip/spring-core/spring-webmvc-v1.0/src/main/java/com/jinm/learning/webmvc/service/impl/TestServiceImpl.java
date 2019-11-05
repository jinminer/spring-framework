package com.jinm.learning.webmvc.service.impl;

import com.jinm.learning.webmvc.core.annotation.JMService;
import com.jinm.learning.webmvc.service.ITestService;

/**
 * @author jinm 2019/11/06 01:38.
 */

@JMService
public class TestServiceImpl implements ITestService {

    public void testService(String name) {
        System.out.println("hello " + name);
    }

}
