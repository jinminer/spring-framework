package com.jinm.demo.service.impl;

import com.jinm.demo.service.IDemoService;
import com.jinm.spring.framework.annotation.JMService;

/**
 * 核心业务逻辑
 */
@JMService
public class DemoService implements IDemoService {

	public String get(String name) {
		return "My name is " + name + ",from service.";
	}

}
