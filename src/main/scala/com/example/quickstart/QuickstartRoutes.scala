package com.example.quickstart

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

trait AbstractQuickstartRoutes[F[_]] {
  def jokeRoutes: HttpRoutes[F]
  def helloWorldRoutes: HttpRoutes[F]
  def greetingRoutes: HttpRoutes[F]
}

class QuickstartRoutes[F[_]](
    greetingDatabase: GreetingDatabase[F],
    helloWorldService: HelloWorldService[F],
    jokeService: JokeService[F]
)(implicit sync: Sync[F])
    extends AbstractQuickstartRoutes[F] {
  def jokeRoutes: HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}

    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "joke" =>
        for {
          joke <- jokeService.get
          resp <- Ok(joke)
        } yield resp
    }
  }

  def helloWorldRoutes: HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        for {
          greeting <- helloWorldService.hello(name)
          resp <- Ok(greeting)
        } yield resp
    }
  }

  def greetingRoutes: HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "greeting" / name / greeting =>
        for {
          greeting <- greetingDatabase.save(name, greeting)
          resp <- Ok(())
        } yield resp
    }
  }
}
