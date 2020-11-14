package com.kalarikkal

import com.kalarikkal.entity.Video
import io.quarkus.runtime.StartupEvent
import org.hibernate.reactive.mutiny.Mutiny.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.util.concurrent.CompletableFuture
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.inject.Inject

@ApplicationScoped
class StartUp
{
  val log: Logger = LoggerFactory.getLogger(StartUp::class.java)


  @Inject
  lateinit var sessionFactory: SessionFactory


  fun start(@Observes startupEvent: StartupEvent)
  {
    var name: String
    var subject: String
    var date: String

    CompletableFuture.runAsync {
      File("../../../../src/assets/videos/").walkTopDown()
        .forEach { it ->
          if (it.isFile)
          {
            name = it.name
            subject = it.name.split(",")[0]
            date = it.name.split(",")[1].split(".")[0]
            this.sessionFactory.withTransaction { session: Session, _: Transaction? ->
              session.persist(Video(name, subject, date))
            }.await().indefinitely()
          }
        }
    }
      .thenAccept { log.info("Videos saved") }
  }

}
