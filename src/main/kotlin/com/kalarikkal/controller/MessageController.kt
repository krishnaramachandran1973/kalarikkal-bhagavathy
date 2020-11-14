package com.kalarikkal.controller


import com.kalarikkal.entity.Message
import io.quarkus.vertx.web.Body
import io.quarkus.vertx.web.Route
import io.smallrye.mutiny.Uni
import io.vertx.core.http.HttpMethod
import org.hibernate.reactive.mutiny.Mutiny.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class MessageController
{
  val log: Logger = LoggerFactory.getLogger(MessageController::class.java)

  @Inject
  lateinit var sessionFactory: SessionFactory

  @Route(path = "/api/message", methods = [HttpMethod.POST], consumes = ["application/json"])
  fun save(@Body message: Message): Uni<Boolean>
  {
    log.info("Received {}", message)

    return this.sessionFactory.withTransaction { session: Session, _: Transaction ->
      session.persist(message)
    }
      .onItem()
      .transform { true }
  }
}
