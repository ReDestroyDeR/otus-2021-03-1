package me.chuwy.otusfp.homework

import cats.effect.implicits._
import cats.effect.{IO, IOApp, Ref}
import cats.implicits._
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.{HttpApp, HttpRoutes}

import scala.concurrent.ExecutionContext.global

object Application extends IOApp.Simple {

  def services: IO[HttpRoutes[IO]] = for {
    counter <- Ref[IO].of(0)
    counterService <- IO.delay(new CounterRestController(counter).counterService)
    slowService <- IO.pure(SlowRestController.slowService)
  } yield counterService <+> slowService

  def httpApp: IO[HttpApp[IO]] = services.map(svc => Router(
    "/" -> svc
  ).orNotFound)

  def server: IO[BlazeServerBuilder[IO]] = httpApp.map(
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(_)
  )


  override def run: IO[Unit] = for {
    _ <- server.flatMap(_.allocated) *> IO.never
  } yield ()
}
