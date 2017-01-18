package controllers

import java.util.UUID
import javax.inject._

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.RegionUtils
import com.amazonaws.services.s3.AmazonS3Client
import com.datastax.driver.core.PagingState
import models.{AppDatabase, Pin, PinJson}
import org.joda.time.DateTime
import play.api.{Environment, Mode, Configuration}
import play.api.mvc._
import utils.Tools

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Mac on 01/10/2016.
  */

@Singleton
class PinController @Inject()(environment: Environment, configuration: Configuration) extends Controller {

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("file").map { picture =>
      val filename = picture.filename
      val file = picture.ref.file

      val fileExtension = filename.split('.').last

      if (Tools.isFileSupported(fileExtension)){
        val bucketName = configuration.underlying.getString("aws.bucketName")
        val AWS_ACCESS_KEY = configuration.underlying.getString("aws.accessKeyId")
        val AWS_SECRET_KEY = configuration.underlying.getString("aws.secretKey")

        val yourAWSCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)
        val amazonS3Client = new AmazonS3Client(yourAWSCredentials)

        val fileName = "images/" + Tools.uuid() + ".jpg"

        amazonS3Client.setRegion(RegionUtils.getRegion(configuration.underlying.getString("aws.region")))
        //                use this only on production because of AWS free tier limits
        if (environment.mode.eq(Mode.Prod)) {
          amazonS3Client.putObject(bucketName, fileName, file)
        }

        Ok("https://s3.eu-central-1.amazonaws.com/play-pin/" + fileName)
      } else{
        Ok("File not supported")
      }

    }.getOrElse {
      Ok("ERROR")
    }
  }

  def addPin() = Action.async(parse.json) { request =>
    AppDatabase.pins.createTable()
    val pin = Pin((request.body \ "board").as[String],
      DateTime.now(),
      UUID.randomUUID(),
      PinJson((request.body \ "title").as[String],
        (request.body \ "description").as[String],
        (request.body \ "url").as[String]))
    AppDatabase.pins.store(pin).map{
      _ => Ok("ok")
    }
  }

  def getAllPins(board: String) = Action.async {
    AppDatabase.pins.createTable()
    AppDatabase.pins.getAll(board).map{
      pins => Ok(views.html.all(pins, board))
    }
  }

  // I didn't figure how hou to get the current pagingState
  //    def getPage(board: String, page: String) = Action.async {
  //        AppDatabase.pins.createTable()
  //        AppDatabase.pins.getPage(20, board, Option(PagingState.fromString(page))).map{
  //            (pins, pagingState) => Ok(views.html.page(pins,pagingState.toString))
  //        }
  //    }

  //    def getFirstPage(board: String) = Action.async {
  //        AppDatabase.pins.getFirstPage(20,board).map{
  //            case (pins, pagingState) => println(pagingState)
  //                    Ok(views.html.page(pins,pagingState.toString))
  //            case _ => Ok("shouldn't go here")
  //        }
  //    }

  def uploadPin () = Action.async {
    AppDatabase.boards.createTable()
    AppDatabase.boards.getAll().map{
      boards => Ok(views.html.uploadPin(boards))
    }
  }
}
