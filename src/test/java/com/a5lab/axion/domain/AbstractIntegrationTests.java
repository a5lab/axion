package com.a5lab.axion.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.a5lab.axion.AxionApplication;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = AxionApplication.class)
public abstract class AbstractIntegrationTests extends AbstractAnyTests {
  final protected static int port = 8080;
  final protected static String baseUrl = "http://127.0.0.1:";

  @Autowired
  protected TestRestTemplate restTemplate;
}