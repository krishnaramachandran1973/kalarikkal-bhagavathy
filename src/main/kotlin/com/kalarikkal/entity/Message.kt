package com.kalarikkal.entity


import io.quarkus.hibernate.reactive.panache.PanacheEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Message : PanacheEntity()
{
  lateinit var name: String
  lateinit var email: String
  lateinit var phone: String
  lateinit var status: String

  @Column(length = 600)
  lateinit var message: String
  override fun toString(): String
  {
    return "Message(name='$name', email='$email', phone='$phone', status='$status', message='$message')"
  }


}
