package me.chuwy.otusfp.homework

import cats.Functor
import cats.effect.{IO, Ref}
import cats.effect.kernel.Concurrent
import cats.implicits.toFunctorOps
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class Counter(counter: Ref[IO, Int])

object Counter {
  def apply: IO[Counter] = Ref[IO].of(0).map(new Counter(_))

  implicit val decoder: Decoder[Counter] = deriveDecoder
  implicit val encoder: Encoder[Counter] = deriveEncoder
}
