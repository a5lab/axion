package com.a5lab.axion.domain.tenant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.a5lab.axion.domain.AbstractIntegrationTests;


class TenantIntegrationTests  extends AbstractIntegrationTests {

  @Test
  public void shouldGetTenants() {
    /*

    List<String> expectedVINList = Stream.of("FR45212A24D4SED66", "FR4EDED2150RFT5GE", "XDFR6545DF3A5R896",
        "XDFR64AE9F3A5R78S", "PQERS2A36458E98CD", "194678S400005", "48955460210").collect(Collectors.toList());

    ResponseEntity<List<Vehicle>> responseEntity = this.restTemplate.exchange(baseUrl + port + "/demo/vehicles",
        HttpMethod.GET, null, new ParameterizedTypeReference<List<Vehicle>>() {
        });

    List<Vehicle> vehiclesResponseList = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertTrue(vehiclesResponseList.size() > 7);
    assertTrue(vehiclesResponseList.stream().anyMatch((vehicle) -> {
      return expectedVINList.contains(vehicle.getVin());
    }));
     */
  }
}