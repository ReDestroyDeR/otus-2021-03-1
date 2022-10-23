package me.chuwy.otusfp.homework

import cats.effect.IO
import cats.{Functor, Monad}
import cats.effect.kernel.Concurrent
import org.http4s.HttpRoutes
import org.http4s.Uri.Path.Root
import org.http4s.dsl.io._
import org.http4s.implicits._
import cats.implicits._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder

class SlowRestController[F[_] : Monad] {

  def slowService: HttpRoutes[F] = HttpRoutes.of{
    case GET -> Root / "slow" / chunk / total / time => Monad[F].pure("Ok")
  }
}
