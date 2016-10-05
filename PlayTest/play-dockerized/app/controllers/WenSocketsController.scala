package controllers

import javax.inject._

import akka.stream.scaladsl.Flow
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.RegionUtils
import com.amazonaws.services.s3.AmazonS3Client
import play.api._
import play.api.libs.json.{JsValue, Json}
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import utils.Tools

import scala.util.Random

// Question : How to avoid using current and use Dependency Injection instead?


import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Mac on 01/10/2016.
  */

@Singleton
class WebSocketsController @Inject() extends Controller {

    def web_sockets = Action {
        Ok(views.html.web_sockets(endpoint = "/ws/get-new-images"))
    }

//    def echo = WebSocket.accept[String, String] { request =>   // WebSocket.accept[Input, Output]
//        // Create a Flow based on an actor
//        ActorFlow.actorRef(out => CustomWebSocketActor.props(out)) // Akka Actor based using CustomWebSocketActor, results in a Flow[Input, Output]
//    }

    def complexJson = WebSocket.accept[JsValue, JsValue] { request => // This WebSocket accepts and returns JsValue (JSON objects)
        Flow.fromFunction { in =>
            Json.obj( // Construct a JSON object using Play Json
                "original-request" -> in,
                "some-string"  -> Random.nextString(10),
                "some-int"     -> Random.nextInt(),
                "some-boolean" -> Random.nextBoolean(),
                "some-double"  -> Random.nextDouble(),
                "some-object-list" -> Json.arr(
                    Json.obj("another-string" -> Random.nextString(5)),
                    Json.obj("another-string" -> Random.nextString(5)),
                    Json.obj("another-string" -> Random.nextString(5))
                )
            )
        }
    }

}
