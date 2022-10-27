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
import scala.language.postfixOps

object SlowRestController {

  def slowService: HttpRoutes[IO] = HttpRoutes.of{
    case r @ GET -> Root / "slow" / IntVar(chunk) / IntVar(total) / IntVar(time) =>
      Ok(
        fs2.Stream.evalSeq(Random.scalaUtilRandom[IO]
                                 .flatMap(_.nextBytes(total)
                                           .map(_.toSeq)))
          .chunkLimit(chunk)
          .metered(time seconds)
      )
  }
}
