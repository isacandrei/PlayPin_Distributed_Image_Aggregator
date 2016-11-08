package controllers

import javax.inject._

import akka.actor._
import akka.stream.Materializer
import play.api.Logger
import play.api.Play.current
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.WSClient
import redis.RedisClient

import scala.concurrent.Future
/**
  * Created by Mac on 01/10/2016.
  */

@Singleton
class WebSocketsController @Inject()(implicit actorSystem: ActorSystem, materializer: Materializer, ws: WSClient) extends Controller {

    val log = Logger.logger

//    val redis = RedisClient("127.0.0.1",32768)
    val redis = RedisClient("redis.weave.local",6379)

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
