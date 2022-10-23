package me.chuwy.otusfp.homework

import cats.effect.IO
import cats.effect.implicits._
import cats.effect.std.Random
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.Uri.Path.Root
import org.http4s.dsl.io._
import org.http4s.implicits._

import scala.concurrent.duration.DurationInt

object SlowRestController {

  def slowService: HttpRoutes[IO] = HttpRoutes.of{
    case r @ GET -> Root / "slow" / IntVar(chunk) / IntVar(total) / IntVar(time) =>
      Ok(fs2.Stream.awakeEvery[IO](time.seconds)
        .evalMap(m => Random.scalaUtilRandom[IO].flatMap(_.nextBytes(chunk)))
        .flatMap(b => fs2.Stream(b:_*))
        .take(total))
  }
}
