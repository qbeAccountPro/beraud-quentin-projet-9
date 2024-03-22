package com.medilaboSolutions.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Some Javadoc :
 * 
 * Interface defining the Feign client for interacting with the Gateway
 * microservice.
 */
@ComponentScan
@FeignClient(name = "microservice-gateway", url = "${microservice-gateway}")
public interface GatewayProxy {

  /**
   * Some Javadoc :
   * 
   * Sends a request to the Gateway microservice to perform a logout action.
   */
  @GetMapping(value = "/app-logout")
  void getLogout();
}
