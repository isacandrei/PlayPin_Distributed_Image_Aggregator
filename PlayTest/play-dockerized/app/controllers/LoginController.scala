package controllers


import models.User
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.libs.json.{Reads, _}
import play.api.libs.json._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.functional.syntax._
import javax.inject.{Inject, Singleton}

import play.api.Logger

/**
  * Created by lex on 28/09/16.
  */
@Singleton
class LoginController @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport{



def index = Action {

        Ok(views.html.login(userForm))
  }

  val userForm : Form[User] = Form{
    mapping(
      "username" -> nonEmptyText(6),
      "password" -> nonEmptyText
    )(User.apply)(User.unapply)
  }

  implicit val ReadsUser: Reads[User] = (
      (__ \ "usermane").read[String] and
      (__ \ "password").read[String]
    )(User.apply _)

  implicit val writesItem = Writes[User] {
    case User(username, password) =>
      Json.obj(
        "username" -> username,
        "password" -> password
      )
  }

  def addUser = Action(parse.urlFormEncoded) { implicit request =>
    val user = userForm.bindFromRequest.get
    println(user)
    Redirect(routes.HomeController.index)
  }

}
