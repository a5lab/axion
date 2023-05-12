package com.a5lab.axion.domain;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractWebIntegrationTests  extends AbstractIntegrationTests{
  protected WebClient webClient;

  @BeforeEach
  public void init(){
    webClient = new WebClient();
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setJavaScriptEnabled(false);
    webClient.setJavaScriptTimeout(10000);
    webClient.getOptions().setTimeout(10000);
  }

  @AfterEach
  public void close(){
    webClient.close();
  }
}