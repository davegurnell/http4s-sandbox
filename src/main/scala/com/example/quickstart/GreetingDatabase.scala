package com.example.quickstart

import cats.effect.Sync
import cats.implicits._

trait GreetingDatabase[F[_]] {
  def read(name: String): F[String]
  def save(name: String, greeting: String): F[Unit]
}

class MockGreetingDatabase[F[_]](implicit sync: Sync[F])
    extends GreetingDatabase[F] {
  var greetings: Map[String, String] = Map()
  val defaultGreeting = "Hello"

  override def read(name: String): F[String] =
    greetings.getOrElse(name, defaultGreeting).pure[F]

  override def save(name: String, greeting: String): F[Unit] =
    sync.defer {
      greetings = greetings + (name -> greeting)
      ().pure[F]
    }
}
