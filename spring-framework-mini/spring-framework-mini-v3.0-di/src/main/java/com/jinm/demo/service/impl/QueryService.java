package com.jinm.demo.service.impl;

import com.jinm.demo.service.IModifyService;
import com.jinm.demo.service.IQueryService;
import com.jinm.spring.framework.annotation.JMAutowired;
import com.jinm.spring.framework.annotation.JMService;

@JMService
public class QueryService implements IQueryService {

    @JMAutowired
    private IModifyService modifyService;

}
