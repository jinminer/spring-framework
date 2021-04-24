package com.jinm.demo.service.impl;

import com.jinm.demo.service.IReferenceAService;
import com.jinm.demo.service.IReferenceCService;
import com.jinm.spring.framework.annotation.JMAutowired;
import com.jinm.spring.framework.annotation.JMService;

@JMService
public class ReferenceAService implements IReferenceAService {

    @JMAutowired
    private IReferenceCService referenceCService;

}
