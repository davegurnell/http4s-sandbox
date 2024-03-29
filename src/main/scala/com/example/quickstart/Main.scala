package com.example.quickstart

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    QuickstartServer.stream[IO].compile.drain.as(ExitCode.Success)
}
