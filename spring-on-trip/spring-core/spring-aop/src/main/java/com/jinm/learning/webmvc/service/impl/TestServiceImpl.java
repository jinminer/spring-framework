package com.jinm.learning.webmvc.service.impl;

import com.jinm.learning.webmvc.core.annotation.JMService;
import com.jinm.learning.webmvc.service.ITestService;

/**
 * @author jinm 2019/11/06 01:38.
 */

@JMService
public class TestServiceImpl implements ITestService {

    @Override
    public String testService(String name) {
        return "hello " + name;
    }

}
