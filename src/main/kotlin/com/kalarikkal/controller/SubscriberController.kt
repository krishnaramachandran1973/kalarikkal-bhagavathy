package com.kalarikkal.controller


import com.kalarikkal.entity.Subscriber
import io.quarkus.mailer.MailTemplate
import io.quarkus.vertx.web.Body
import io.quarkus.vertx.web.Route
import io.smallrye.mutiny.Uni
import io.vertx.core.http.HttpMethod
import io.vertx.reactivex.core.http.HttpServerRequest
import io.vertx.reactivex.ext.web.RoutingContext
import org.hibernate.reactive.mutiny.Mutiny.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URL
import java.util.concurrent.CompletableFuture
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@ApplicationScoped
class SubscriberController
{

  val log: Logger = LoggerFactory.getLogger(SubscriberController::class.java)

  @Inject
  lateinit var sessionFactory: SessionFactory

  @Inject
  lateinit var subscription: MailTemplate

  @Route(path = "api/subscribe", methods = [HttpMethod.POST], consumes = ["application/json"])
  fun saveSubscriber(@Body subscriber: Subscriber, httpServerRequest: HttpServerRequest): Uni<Boolean>
  {
    val url = URL(httpServerRequest.absoluteURI())
    return this.sessionFactory.withTransaction { session: Session, _: Transaction ->
      session.persist(subscriber)
    }
      .eventually {
        CompletableFuture.supplyAsync {
          subscription.to(subscriber.email)
            .subject("Verification Service: Please verify your email!")
            .data("title", "KalarikkalBhagavathy.in")
            .data("header", "Welcome to KalarikkalBhagavathy.in!")
            .data("domain", "KalarikkalBhagavathy")
            .data("name", subscriber.name)
            .data("description", "Thank you for subscribing to KalarikkalBhagavathy.com. You will be notified of any upcoming festivities or videos. Meanwhile please go through the\n" +
              "          various testimonials, videos and consultation types.")
            .data("verify", "Please verify your email by clicking on the verify link to complete subscription.")
            .data("url", url.protocol + "://" + url.authority)
            .data("code", subscriber.code)
            .send()
            .thenAccept { log.info("Mail send to {}", subscriber.email) }
        }
      }
      .onItem()
      .transform { true }
  }

  @Route(path = "api/subscribe/verify/:code", methods = [HttpMethod.GET])
  fun verify(routingContext: RoutingContext): Uni<Subscriber>
  {
    val code = routingContext.request()
      .getParam("code")
    log.info("Verifying code {}", code)

    val criteriaBuilder = sessionFactory.criteriaBuilder
    val criteriaQuery: CriteriaQuery<Subscriber> = criteriaBuilder.createQuery(Subscriber::class.java)
    val root: Root<Subscriber> = criteriaQuery.from(Subscriber::class.java)

    criteriaQuery.select(root)
      .where(criteriaBuilder.equal(root.get<String>("code"), code))

    return this.sessionFactory.withTransaction { session: Session, _: Transaction ->
      session.createQuery(criteriaQuery).singleResult
        .onItem()
        .call { subscriber: Subscriber ->
          subscriber.verified = true
          session.flush()
        }
    }
  }
}
