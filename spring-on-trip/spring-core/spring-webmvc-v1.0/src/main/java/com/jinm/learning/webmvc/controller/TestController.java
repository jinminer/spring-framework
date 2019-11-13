package com.jinm.learning.webmvc.controller;

import com.jinm.learning.webmvc.core.annotation.JMAutowired;
import com.jinm.learning.webmvc.core.annotation.JMController;
import com.jinm.learning.webmvc.core.annotation.JMRequestMapping;
import com.jinm.learning.webmvc.core.annotation.JMRequestParam;
import com.jinm.learning.webmvc.service.ITestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jinm 019/11/06 01:33.
 */

@JMController
@JMRequestMapping("/jinm")
public class TestController {

    @JMAutowired
    private ITestService testService;

    @JMRequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response, @JMRequestParam("name") String name){

        String result = testService.testService(name);

        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
