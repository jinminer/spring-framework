package com.jinm.learning.webmvc.service.impl;

import com.jinm.learning.webmvc.core.annotation.JMService;
import com.jinm.learning.webmvc.service.ITestService;

/**
 * @author jinm 2019/11/06 01:38.
 */

@JMService
public class TestService implements ITestService {

    @Override
    public String query(String name) {

        System.out.println("");

        return "hello " + name;
    }

    @Override
    public void add() throws Exception {
        throw new Exception("IN method： add error");
    }

}
