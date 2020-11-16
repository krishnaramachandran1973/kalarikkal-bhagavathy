package com.kalarikkal.entity

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import javax.persistence.Entity

@Entity
class Video() : PanacheEntity()
{
  lateinit var name: String
  lateinit var url: String
  lateinit var date: String

  constructor(name: String, url: String, date: String) : this()
  {
    this.name = name
    this.url = url
    this.date = date
  }

}
