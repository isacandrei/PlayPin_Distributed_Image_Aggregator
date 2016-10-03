package controllers

import javax.inject.Inject

import play.api.mvc._

/**
	* Created by lex on 30/09/16.
	*/

class ApplicationController @Inject() extends Controller {


	def home = Action {
		Ok(views.html.home())
	}

	def showBoards = Action {
		Ok(views.html.showBoards())
	}

	def uploadPin = Action {
		Ok(views.html.uploadPin())
	}

}
