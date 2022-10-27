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
  val counterState: IO[Ref[IO, Counter]] = Ref[IO].of(Counter(0))
    .flatTap(IO.println)

  def services: IO[HttpRoutes[IO]] = for {
    state <- counterState
  } yield CounterRestController.counterService(state) <+> SlowRestController.slowService


  def httpApp: IO[HttpApp[IO]] = for {
    services <- services
  } yield Router(
      "/" -> services
    ).orNotFound

  def server: IO[BlazeServerBuilder[IO]] = for {
    httpApp <- httpApp
  } yield BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(httpApp)


  override def run: IO[Unit] = for {
    _ <- server.flatMap(_.allocated) *> IO.never
  } yield ()
}
