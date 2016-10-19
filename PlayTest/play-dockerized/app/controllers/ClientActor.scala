package controllers

/**
  * Created by lex on 17/10/16.
  */

import akka.actor._
import play.api.libs.json.JsValue

class ClientActor(out: ActorRef, chat: ActorRef) extends Actor {

    chat ! Join

    override def postStop() = chat ! Leave

    def receive = {
        // this handles messages from the websocket
        case text: JsValue =>
            chat ! ClientSentMessage(text)

        case ClientSentMessage(text) =>
            out ! text
    }
}
