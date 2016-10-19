package controllers

import javax.inject._

import play.api.mvc._
import akka.actor._
import akka.stream.Materializer
import models.Pin
import play.api.libs.streams.ActorFlow
import play.api.Play.current
import play.api.mvc._
import play.api.libs.iteratee._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
/**
  * Created by Mac on 01/10/2016.
  */

@Singleton
class WebSocketsController @Inject()(implicit actorSystem: ActorSystem, materializer: Materializer) extends Controller {

//    def echo = WebSocket.accept[String, String] { request =>   // WebSocket.accept[Input, Output]
//        // Create a Flow based on an actor
//        ActorFlow.actorRef(out => CustomWebSocketActor.props(out)) // Akka Actor based using CustomWebSocketActor, results in a Flow[Input, Output]
//    }

    val chat = actorSystem.actorOf(Props[Chat], "chat")

    /*
     Specifies how to wrap an out-actor that will represent
     WebSocket connection for a given request.
    */
    def socket = WebSocket.acceptWithActor[JsValue, JsValue] {
        (request: RequestHeader) =>
            (out: ActorRef) =>
                Props(new ClientActor(out, chat))
    }
}
