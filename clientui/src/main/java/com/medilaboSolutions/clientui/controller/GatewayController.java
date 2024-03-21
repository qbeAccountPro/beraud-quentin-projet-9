package com.medilaboSolutions.clientui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.medilaboSolutions.clientui.proxies.GatewayProxy;

@Controller
public class GatewayController {

  private final GatewayProxy gatewayProxy;

  public GatewayController(GatewayProxy gatewayProxy) {
    this.gatewayProxy = gatewayProxy;
  }

  @GetMapping("/app-logout")
  public void getLogout() {
    gatewayProxy.getLogout();
  }
}
