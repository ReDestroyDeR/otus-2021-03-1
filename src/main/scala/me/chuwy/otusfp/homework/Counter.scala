package me.chuwy.otusfp.homework

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class Counter(counter: Int) {
  def +(increment: Int): Counter = Counter(counter + increment)

  def ++ : Counter = Counter(counter + 1)
}

object Counter {
  def apply(counter: Int): Counter = new Counter(counter)

  implicit class CounterSyntax(counter: Counter) {
    def +(increment: Int): Counter = counter + increment
    def ++ : Counter = counter.++
  }

  implicit val decoder: Decoder[Counter] = deriveDecoder[Counter]
  implicit val encoder: Encoder[Counter] = deriveEncoder[Counter]
}
