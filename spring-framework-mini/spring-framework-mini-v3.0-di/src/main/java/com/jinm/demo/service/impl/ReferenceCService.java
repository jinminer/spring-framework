package com.jinm.demo.service.impl;

import com.jinm.demo.service.IReferenceBService;
import com.jinm.demo.service.IReferenceCService;
import com.jinm.spring.framework.annotation.JMAutowired;
import com.jinm.spring.framework.annotation.JMService;

@JMService
public class ReferenceCService implements IReferenceCService {

    @JMAutowired
    private IReferenceBService referenceBService;

}
