package controllers

import javax.inject._

import models.{AppDatabase, Board}
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
  * Created by Mac on 01/10/2016.
  */

@Singleton
class BoardController @Inject() extends Controller {

    def addBoard = Action.async(parse.text) { request =>
        AppDatabase.boards.createTable()
        AppDatabase.boards.store(Board(request.body)).map{
                    _ => Ok("ok")
        }
    }

    def createBoard = Action {
        Ok(views.html.createBoard())
    }

    def showBoards = Action.async {
        AppDatabase.boards.createTable()
        AppDatabase.boards.getAll().map{
            boards => Ok(views.html.showBoards(boards, true))
        }
    }

    def showBoardsAjax = Action.async {
        AppDatabase.boards.createTable()
        AppDatabase.boards.getAll().map{
            boards => Ok(views.html.showBoards(boards, false))
        }
    }

}
