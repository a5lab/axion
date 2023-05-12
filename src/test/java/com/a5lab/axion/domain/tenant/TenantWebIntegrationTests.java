package com.a5lab.axion.domain.tenant;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.domain.AbstractWebIntegrationTests;


class TenantWebIntegrationTests extends AbstractWebIntegrationTests {

  @Autowired
  private TenantService tenantService;

  @Test
  public void shouldGetTenants() throws Exception{
    final HtmlPage page = webClient.getPage(baseUrl + port + "/settings/tenants");
    Assertions.assertEquals("Dashboard title", page.getTitleText());
  }
}
