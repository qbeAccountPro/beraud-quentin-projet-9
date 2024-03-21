package com.medilaboSolutions.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;

@ComponentScan
@FeignClient(name = "microservice-gateway", url = "${microservice-gateway}")
public interface GatewayProxy {
  @GetMapping(value = "/app-logout")
  void getLogout();
}
