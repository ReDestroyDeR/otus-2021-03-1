package me.chuwy.otusfp.homework

import cats.Monad
import cats.effect.kernel.Concurrent
import cats.effect.{Async, IO, IOApp}
import cats.implicits._
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.{HttpApp, HttpRoutes}
import org.http4s.implicits._
import org.http4s.server.Router

import scala.concurrent.ExecutionContext.global

object Application extends IOApp.Simple {
  def services[F[_]: Monad: Concurrent]: HttpRoutes[F] =
    new CounterRestController[F].counterService <+> new SlowRestController[F].slowService

  def httpApp[F[_]: Monad: Concurrent]: HttpApp[F] = Router(
      "/" -> services[F]
    ).orNotFound

  def server[F[_]: Async] =
    BlazeServerBuilder[F](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(httpApp[F])


  override def run: IO[Unit] = for {
    _ <- server[IO].allocated.foreverM
  } yield ()
}
