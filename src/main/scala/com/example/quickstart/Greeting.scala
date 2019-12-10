package com.example.quickstart

import cats.Monad
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

final case class Greeting(greeting: String) extends AnyVal

object Greeting {
  implicit val greetingEncoder: Encoder[Greeting] = new Encoder[Greeting] {
    final def apply(a: Greeting): Json = Json.obj(
      ("message", Json.fromString(a.greeting))
    )
  }

  implicit def greetingEntityEncoder[F[_]: Monad]: EntityEncoder[F, Greeting] =
    jsonEncoderOf[F, Greeting]
}
