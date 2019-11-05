package com.jinm.learning.webmvc.controller;

import com.jinm.learning.webmvc.core.annotation.JMController;
import com.jinm.learning.webmvc.core.annotation.JMRequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jinm 019/11/06 01:33.
 */

@JMController
@JMRequestMapping("/jinm/webmvc")
public class TestController {

    @JMRequestMapping("/test")
    public String test(String name){
        return "hello" + name;
    }

}
