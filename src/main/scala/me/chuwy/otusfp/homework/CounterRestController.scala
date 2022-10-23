package me.chuwy.otusfp.homework

import cats.Functor
import cats.effect.kernel.Concurrent
import org.http4s.HttpRoutes
import org.http4s.Uri.Path.Root
import org.http4s.dsl.io._
import org.http4s.implicits._
import cats.implicits._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder

class CounterRestController[F[_] : Concurrent : Functor] {
  val counterState: F[Counter[F]] = Counter[F]

  def counterService: HttpRoutes[F] = HttpRoutes.of {
    case POST -> Root / "counter" => counterState.map(_.counter.getAndUpdate(_ + 1))
  }
}
