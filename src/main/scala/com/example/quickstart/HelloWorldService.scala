package com.example.quickstart

import cats.Monad
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait HelloWorldService[F[_]] {
  def hello(name: String): F[Greeting]
}

class GenericHelloWorldService[F[_]](
    greetingDatabase: GreetingDatabase[F]
)(implicit monad: Monad[F])
    extends HelloWorldService[F] {
  def hello(name: String): F[Greeting] = {
    for {
      greeting <- greetingDatabase.read(name)
    } yield Greeting(greeting + " " + name)
  }
}
