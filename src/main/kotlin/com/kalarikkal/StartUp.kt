package com.kalarikkal

import com.kalarikkal.entity.Video
import io.quarkus.runtime.StartupEvent
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListBucketsRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
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
    log.info("Startup running")
    var name: String
    var url: String
    var date: String

    CompletableFuture.runAsync {
      val region = Region.AP_SOUTH_1
      val credentials: InstanceProfileCredentialsProvider =
        InstanceProfileCredentialsProvider.builder()
          .asyncCredentialUpdateEnabled(true)
          .build()

      val s3 = S3Client.builder()
        .region(region)
        .build()

      val listBucketsRequest = ListBucketsRequest.builder()
        .build()
      val listBucketsResponse = s3.listBuckets(listBucketsRequest)
      val bucket = listBucketsResponse.buckets()
        .stream()
        .filter { it.name() == "kalarikkal-bhagavathy" }
        .findFirst()

      val listObjectsV2Request = ListObjectsV2Request.builder()
        .bucket(bucket.get()
          .name())
        .build()

      val listObjectsV2Response = s3.listObjectsV2(listObjectsV2Request)
      listObjectsV2Response.contents()
        .forEach {
          log.info(it.key())
          name = it.key()
            .split(",")[0]
          url = "https://${bucket.get()
            .name()}.s3.amazonaws.com/${it.key()}"
          date = it.key()
            .split(",")[1].split(".")[0]
          this.sessionFactory.withTransaction { session: Mutiny.Session, _: Mutiny.Transaction? ->
            session.persist(Video(name, url, date))
          }
            .await()
            .indefinitely()

        }
      credentials.close()

    }.thenAccept { log.info("Fetching and saving videos complete!!") }

  }

}
