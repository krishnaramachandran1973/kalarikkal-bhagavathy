package com.kalarikkal.controller

import com.kalarikkal.entity.Video
import io.quarkus.vertx.web.Route
import io.smallrye.mutiny.Uni
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class VideoController
{
  val log: Logger = LoggerFactory.getLogger(VideoController::class.java)

  @Inject
  lateinit var sessionFactory: SessionFactory

  @Route(path = "api/videos", produces = ["application/json"])
  fun allVideos(): Uni<List<Video>>
  {
    log.info("Getting all videos")
    return this.sessionFactory.withSession {
      it.createQuery<Video>("from Video").resultList
    }
  }

}
