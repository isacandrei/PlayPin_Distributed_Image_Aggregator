package controllers

import javax.inject._

import models.User
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo._
import reactivemongo.api.ReadPreference
import reactivemongo.play.json._
import reactivemongo.play.json.collection._
import utils.Errors

import scala.concurrent.{ExecutionContext, Future}
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (val reactiveMongoApi: ReactiveMongoApi)(implicit exec: ExecutionContext) extends Controller with MongoController with ReactiveMongoComponents {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

    val imgs = Array("http://kurld.com/images/wallpapers/images/images-22.jpg",
        "http://www.freedigitalphotos.net/images/img/homepage/87357.jpg",
        "https://pixabay.com/static/uploads/photo/2015/08/14/08/29/images-888133_960_720.jpg")


    def index = Action {
        Ok(views.html.index("."))
    }

    def home = Action {
        Ok(views.html.home(imgs))
    }


  def usersFuture: Future[JSONCollection] = database.map(_.collection[JSONCollection]("user"))

  def create(username: String, password: String) = Action.async {
    for {
      users <- usersFuture
      lastError <- users.insert(User(username, password))
    } yield
      Ok("Mongo LastError: %s".format(lastError))
  }

  def createFromJson = Action.async(parse.json) { request =>
    Json.fromJson[User](request.body) match {
      case JsSuccess(user, _) =>
        for {
          users <- usersFuture
          lastError <- users.insert(user)
        } yield {
          Logger.debug(s"Successfully inserted with LastError: $lastError")
          Created("Created 1 user")
        }
      case JsError(errors) =>
        Future.successful(BadRequest("Could not build a user from the json provided. " + Errors.show(errors)))
    }
  }

  def createBulkFromJson = Action.async(parse.json) { request =>
    Json.fromJson[Seq[User]](request.body) match {
      case JsSuccess(newUsers, _) =>
        usersFuture.flatMap { users =>
          val documents = newUsers.map(implicitly[users.ImplicitlyDocumentProducer](_))

          users.bulkInsert(ordered = true)(documents: _*).map { multiResult =>
            Logger.debug(s"Successfully inserted with multiResult: $multiResult")
            Created(s"Created ${multiResult.n} users")
          }
        }
      case JsError(errors) =>
        Future.successful(BadRequest("Could not build a user from the json provided. " + Errors.show(errors)))
    }
  }

  def findByUsername(username: String) = Action.async {
    // let's do our query
    val futureusersList: Future[List[User]] = usersFuture.flatMap {
      // find all users with name `name`
      _.find(Json.obj("username" -> username)).
        // perform the query and get a cursor of JsObject
        cursor[User](ReadPreference.primary).
        // Coollect the results as a list
        collect[List]()
    }

    // everything's ok! Let's reply with a JsValue
    futureusersList.map { users =>
      Ok(Json.toJson(users))
    }
  }

}

