package me.chuwy.otusfp.homework

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class Counter(counter: Int)
object Counter {
  def apply(counter: Int): Counter = new Counter(counter)

  implicit val decoder: Decoder[Counter] = deriveDecoder[Counter]
  implicit val encoder: Encoder[Counter] = deriveEncoder[Counter]
}
