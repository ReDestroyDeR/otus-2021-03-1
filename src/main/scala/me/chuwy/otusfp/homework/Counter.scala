package me.chuwy.otusfp.homework

import cats.Functor
import cats.effect.Ref
import cats.effect.kernel.Concurrent
import cats.implicits.toFunctorOps
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class Counter[F[_]](counter: Ref[F, Int])

object Counter {
  def apply[F[_] : Concurrent : Functor]: F[Counter[F]] = Ref.of(0).map(Counter[F])

  implicit def decoder[F[_]]: Decoder[Counter[F]] = deriveDecoder
  implicit def encoder[F[_]]: Encoder[Counter[F]] = deriveEncoder
}
