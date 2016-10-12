package controllers

import javax.inject._

import play.api.mvc._
import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.libs.streams.ActorFlow

/**
  * Created by Mac on 01/10/2016.
  */

@Singleton
class WebSocketsController @Inject()(implicit system: ActorSystem, materializer: Materializer) extends Controller {

    def echo = WebSocket.accept[String, String] { request =>   // WebSocket.accept[Input, Output]
        // Create a Flow based on an actor
        ActorFlow.actorRef(out => CustomWebSocketActor.props(out)) // Akka Actor based using CustomWebSocketActor, results in a Flow[Input, Output]
    }

}
