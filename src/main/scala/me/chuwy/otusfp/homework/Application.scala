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
  def services: HttpRoutes[IO] =
    new CounterRestController().counterService <+> new SlowRestController().slowService

  def httpApp: HttpApp[IO] = Router(
      "/" -> services
    ).orNotFound

  def server =
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(httpApp)


  override def run: IO[Unit] = for {
    _ <- server.allocated.foreverM
  } yield ()
}
