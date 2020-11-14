package com.kalarikkal.entity

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import javax.persistence.Entity

@Entity
class Video() : PanacheEntity()
{
  lateinit var name: String
  lateinit var subject: String
  lateinit var date: String

  constructor(name: String, subject: String, date: String) : this()
  {
    this.name = name
    this.subject = subject
    this.date = date
  }

}
