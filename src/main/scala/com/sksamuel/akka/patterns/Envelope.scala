package com.sksamuel.akka.patterns

import java.util.UUID

/** @author Stephen Samuel */
case class Envelope[T](msg: T,
                       correlationId: String = UUID.randomUUID().toString,
                       attributes: Map[Attribute, Any] = Map.empty) {
  def withAttribute(attribute: Attribute,
                    value: Any): Envelope[T] = copy(attributes = attributes + (attribute -> value))
}

object Envelope {
  def apply[T](msg: T) = new
      Envelope(msg = msg, attributes = Map[Attribute, Any](MessageTimestampAttribute -> System.currentTimeMillis()))
}

trait Attribute
case object MessageTimestampAttribute extends Attribute
case object PriorityAttribute extends Attribute
case object SequenceAttribute extends Attribute