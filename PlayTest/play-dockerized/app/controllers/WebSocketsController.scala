package controllers

import javax.inject._

import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.libs.json.{JsValue, Json}
import play.api.libs.streams.ActorFlow
import play.api.mvc._

/**
  * Created by Mac on 01/10/2016.
  */

@Singleton
class WebSocketsController @Inject()(implicit system: ActorSystem, materializer: Materializer)  extends Controller {


    def stringTest = WebSocket.accept[String, String] { request =>   // WebSocket.accept[Input, Output]
        // Create a Flow based on an actor
        ActorFlow.actorRef(out => CustomWebSocketActor.props(out)) // Akka Actor based using CustomWebSocketActor, results in a Flow[Input, Output]
    }


    def web_sockets = Action {
        Ok(views.html.web_sockets(endpoint = "/ws/getNewImages"))
    }


}
