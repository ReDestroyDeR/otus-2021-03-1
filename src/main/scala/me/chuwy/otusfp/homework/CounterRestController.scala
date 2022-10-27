package me.chuwy.otusfp.homework

import cats.effect.{IO, Ref}
import cats.implicits._
import me.chuwy.otusfp.homework.Counter._
import org.http4s.HttpRoutes
import org.http4s.Uri.Path.Root
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.io._

import scala.language.postfixOps

object CounterRestController {
  def counterService(counterState: Ref[IO, Counter]): HttpRoutes[IO] = HttpRoutes.of {
    case POST -> Root / "counter" => Ok(counterState.updateAndGet(_.++()))
  }
}
