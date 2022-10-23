package me.chuwy.otusfp.homework

import cats.effect.{IO, Ref}
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.Uri.Path.Root
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.io._

class CounterRestController(counterState: Ref[IO, Int]) {
  def counterService: HttpRoutes[IO] = HttpRoutes.of {
    case POST -> Root / "counter" => Ok(counterState.updateAndGet(_ + 1).map(Counter(_)))
  }
}
