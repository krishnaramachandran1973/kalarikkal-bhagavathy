package com.kalarikkal.controller

import io.quarkus.vertx.web.Route
import io.vertx.core.http.HttpMethod
import io.vertx.reactivex.ext.web.RoutingContext
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AngularRoutesController
{
  @Route(path = "/about", methods = [HttpMethod.GET], produces = ["text/html"])
  fun about(routingContext: RoutingContext) = routingContext.reroute("/")

  @Route(path = "/contact", methods = [HttpMethod.GET], produces = ["text/html"])
  fun contact(routingContext: RoutingContext) = routingContext.reroute("/")

  @Route(path = "/videos", methods = [HttpMethod.GET], produces = ["text/html"])
  fun blogs(routingContext: RoutingContext) = routingContext.reroute("/")

  @Route(regex = "/verify.*", methods = [HttpMethod.GET], produces = ["text/html"])
  fun verify(routingContext: RoutingContext) = routingContext.reroute("/")
}
