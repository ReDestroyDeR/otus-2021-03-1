package me.chuwy.otusfp.homework

import cats.effect.IO
import cats.effect.implicits._
import cats.effect.std.Random
import cats.implicits._
import io.circe.generic.auto.exportEncoder
import org.http4s.HttpRoutes
import org.http4s.Uri.Path.Root
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.io._
import org.http4s.implicits._

import scala.concurrent.duration.DurationInt

object SlowRestController {

  def slowService: HttpRoutes[IO] = HttpRoutes.of{
    case r @ GET -> Root / "slow" / chunk / total / time =>
      Ok(fs2.Stream.awakeEvery[IO](time.toInt.seconds)
        .evalMap(m => Random.scalaUtilRandom[IO].flatMap(_.nextBytes(chunk.toInt)))
        .flatMap(fs2.Stream(_:_*))
        .take(total.toInt)
        .evalTap(IO.println(_)))
  }
}
