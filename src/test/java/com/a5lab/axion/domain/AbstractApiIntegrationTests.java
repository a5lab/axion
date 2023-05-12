package com.a5lab.axion.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;


public abstract class AbstractApiIntegrationTests  extends AbstractIntegrationTests{
  @Autowired
  protected TestRestTemplate restTemplate;
}