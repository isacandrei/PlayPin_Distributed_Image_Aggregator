package controllers

/**
  * Created by lex on 17/10/16.
  */
import akka.actor._
import play.api.libs.json.JsValue

// our domain message protocol
case object Join
case object Leave
final case class ClientSentMessage(text: JsValue)

// Chat actor
class Chat extends Actor {
    // initial message-handling behavior
    def receive = process(Set.empty)

    def process(subscribers: Set[ActorRef]): Receive = {
        case Join =>
            // replaces message-handling behavior by the new one
            context become process(subscribers + sender)

        case Leave =>
            context become process(subscribers - sender)

        case msg: ClientSentMessage =>
            // send messages to all subscribers except sender
            (subscribers - sender).foreach { _ ! msg }
    }
}
