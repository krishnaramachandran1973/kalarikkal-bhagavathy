package com.kalarikkal

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.kalarikkal.entity.Video
import io.quarkus.runtime.StartupEvent
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

  @ConfigProperty(name = "key_id")
  lateinit var amazon_key: String

  @ConfigProperty(name = "secret")
  lateinit var amazon_secret: String

  fun start(@Observes startupEvent: StartupEvent)
  {
    var name: String
    var url: String
    var date: String

    CompletableFuture.runAsync {
      val awsCredentials: AWSCredentials = BasicAWSCredentials(amazon_key, amazon_secret)
      val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard()
        .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
        .withRegion(Regions.US_EAST_1)
        .build()

      val objectListing: ObjectListing = s3Client.listObjects("kalarikkalbhagavathy")
      objectListing.objectSummaries.forEach { s3ObjectSummary: S3ObjectSummary ->
        name = s3ObjectSummary.key.split(",")[0]
        url = "https://kalarikkalbhagavathy.s3.amazonaws.com/${s3ObjectSummary.key}"
        date = s3ObjectSummary.key.split(",")[1].split(".")[0]
        this.sessionFactory.withTransaction { session: Mutiny.Session, _: Mutiny.Transaction? ->
          session.persist(Video(name, url, date))
        }
          .await()
          .indefinitely()
      }
    }
      .thenAccept { log.info("Fetching videos from Amazon S3 and data insert complete") }

  }

}
