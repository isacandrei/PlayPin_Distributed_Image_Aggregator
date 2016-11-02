package controllers

import javax.inject._

import play.api.mvc._
import akka.actor._
import akka.stream.Materializer
import models.Pin
import play.api.Logger
import play.api.libs.streams.ActorFlow
import play.api.Play.current
import play.api.mvc._
import play.api.libs.iteratee._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
import play.api.libs.ws.WSClient
import redis.RedisClient

import scala.concurrent.Future
/**
  * Created by Mac on 01/10/2016.
  */

@Singleton
class WebSocketsController @Inject()(implicit actorSystem: ActorSystem, materializer: Materializer, ws: WSClient) extends Controller {

//    def echo = WebSocket.accept[String, String] { request =>   // WebSocket.accept[Input, Output]
//        // Create a Flow based on an actor
//        ActorFlow.actorRef(out => CustomWebSocketActor.props(out)) // Akka Actor based using CustomWebSocketActor, results in a Flow[Input, Output]
//    }
    val log = Logger.logger

    val chat = actorSystem.actorOf(Props[Chat], "chat")

    val redis = RedisClient("127.0.0.1",32768)

    /*
     Specifies how to wrap an out-actor that will represent
     WebSocket connection for a given request.
    */
//    def socket (board: String) = WebSocket.accept[JsValue, JsValue] { request =>
//        ActorFlow.actorRef( out => Props(new ClientActor(out, chat)))
//
//    }

    def socket = WebSocket.tryAcceptWithActor[String, String] { request =>
        def props(channel: String)(out: ActorRef) = Props(classOf[ClientActor], redis, out, Seq(channel), Nil)
                .withDispatcher("rediscala.rediscala-client-worker-dispatcher")
        Future.successful(request.getQueryString("channel") match {
            case None => Left(Forbidden)
            case Some(channel) => Right(props(channel))
        })
    }

    def publish(channel: String) = Action.async { implicit request =>
            println(channel)
        request.body.asFormUrlEncoded.flatMap{ params =>
            params.get("message").map{ message =>
                redis.publish(channel, message.head).map{ n =>
                    if(n > 0) {
                        log.debug(s"Number of subscriber: $n s")
                        Ok(n.toString)
                    }
                    else {
                        log.debug("No recipient")
                        Gone
                    }
                }
            }
        }.getOrElse(Future.successful(BadRequest("No content received")))
    }

}
