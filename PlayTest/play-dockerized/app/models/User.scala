package models

import play.api.data._
import play.api.data.Forms._
import java.util.concurrent.atomic.AtomicLong

import play.api.libs.json.Json

import scala.collection.concurrent.TrieMap
/**
  * Created by lex on 28/09/16.
  */
case class User(username: String, password: String)


object User {

  implicit val personFormat = Json.format[User]

}

////
////case class User(username: String, password: String ) {
////
//////  val userForm = Form(
//////    mapping(
//////      "username" -> nonEmptyText,
//////      "pass" -> nonEmptyText
//////    )(User.apply)(User.unapply)
//////  )
//////
//////
//////  val anyData = Map("username" -> "bob@gmail.com", "pass" -> "18")
//////  val user: User = userForm.bind(anyData).get
////
////}
//case class User(username: String, password: String )
//
//trait UserList {
//  def insert(username: String, password: String)
//}
//
//object UserList extends UserList{
//  private val users = TrieMap.empty[Long, User] // In memory data storage, in reality you would access a database instead !!
//  private val seq = new AtomicLong // Mimic auto-increment IDs
//
//  def insert( username: String, password: String): Option[User] = {
//    val id = seq.incrementAndGet()
//    val item = User(username, password)
//    users.put(id, item)
//    Some(item)
//  }
//
//}
//
////def list(): Seq[Item] = {
////  items.values.to[Seq]
////}