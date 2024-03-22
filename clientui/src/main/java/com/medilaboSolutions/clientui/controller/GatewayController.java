package com.medilaboSolutions.clientui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.medilaboSolutions.clientui.proxies.GatewayProxy;

/**
 * Some Javadoc :
 * 
 * Controller class responsible for handling requests related to "Gateway" operations.
 */
@Controller
public class GatewayController {

  private final GatewayProxy gatewayProxy;

  public GatewayController(GatewayProxy gatewayProxy) {
    this.gatewayProxy = gatewayProxy;
  }

  /** 
   * Some Javadoc :
   * 
   * Method to handling logout request.
   */
  @GetMapping("/app-logout")
  public void getLogout() {
    gatewayProxy.getLogout();
  }
}