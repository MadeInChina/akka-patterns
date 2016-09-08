package com.sksamuel.akka.mailbox

import akka.dispatch.{MessageQueue, Envelope, MailboxType}
import akka.actor.{ActorSystem, ActorRef}
import java.util.concurrent.ConcurrentLinkedQueue
import com.typesafe.config.Config
import scala.concurrent.duration.MILLISECONDS

/** @author Stephen Samuel */
class ExpiringMailbox(settings: ActorSystem.Settings, config: Config) extends MailboxType {

  val expiry = config.getDuration("message-expiry", MILLISECONDS)

  def create(owner: Option[ActorRef], system: Option[ActorSystem]): MessageQueue = new ExpiringMessageQueue

  class ExpiringMessageQueue extends ConcurrentLinkedQueue[ExpiringMessage] with MessageQueue {
    final def enqueue(receiver: ActorRef, e: Envelope): Unit = this add ExpiringMessage(e)
    final def dequeue(): Envelope = {
      Option(this.poll()) match {
        case Some(ex) if ex.expire >= System.currentTimeMillis() => ex.envelope
        case Some(ex) => dequeue()
        case None => null
      }
    }
    def cleanUp(owner: ActorRef, deadLetters: MessageQueue): Unit = {
      if (hasMessages) {
        var envelope = dequeue()
        while (envelope ne null) {
          deadLetters.enqueue(owner, envelope)
          envelope = dequeue()
        }
      }
    }
    def hasMessages: Boolean = !isEmpty
    def numberOfMessages: Int = size()
  }

  case class ExpiringMessage(envelope: Envelope, expire: Long = System.currentTimeMillis() + expiry)
}
