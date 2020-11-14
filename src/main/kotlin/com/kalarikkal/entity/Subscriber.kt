package com.kalarikkal.entity



import io.quarkus.hibernate.reactive.panache.PanacheEntity
import java.util.*
import javax.persistence.Entity

@Entity
class Subscriber : PanacheEntity()
{
  lateinit var name: String
  lateinit var email: String
  var code: String = UUID.randomUUID()
    .toString()
  var verified: Boolean = false
}
