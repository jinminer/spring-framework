package com.jinm.demo.service.impl;

import com.jinm.demo.service.IReferenceAService;
import com.jinm.demo.service.IReferenceBService;
import com.jinm.spring.framework.annotation.JMAutowired;
import com.jinm.spring.framework.annotation.JMService;

@JMService
public class ReferenceBService implements IReferenceBService {

    @JMAutowired
    private IReferenceAService referenceAService;

}
