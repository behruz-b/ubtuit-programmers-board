package controllers

import java.nio.file.{Files, Path}

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import org.webjars.play.WebJarsUtil
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import protocols.AdminProtocol._
import views.html.admin._

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class AdminController @Inject()(val controllerComponents: ControllerComponents,
                                implicit val webJarsUtil: WebJarsUtil,
                                @Named("admin-manager") val adminManager: ActorRef,
                                indexTemplate: index,
                               )
                               (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {

  implicit val defaultTimeout: Timeout = Timeout(60.seconds)

  def index = Action {
    Ok(indexTemplate(Some("leaders")))
  }

  def createUser(): Action[MultipartFormData[TemporaryFile]] = Action.async(parse.multipartFormData) { implicit request: Request[MultipartFormData[TemporaryFile]] => {
    val body = request.body.asFormUrlEncoded
    val firstName = body("firstName").head
    request.body.file("attachedFile").map { tempFile =>
      val fileName = tempFile.filename
      val imgData = getBytesFromPath(tempFile.ref.path)
      Future.successful(Ok("OK"))
    }.getOrElse(Future.successful(BadRequest("Error occurred. Please try again")))
  }
  }

  def createLanguage(): Action[MultipartFormData[TemporaryFile]] = Action.async(parse.multipartFormData) { implicit request: Request[MultipartFormData[TemporaryFile]] => {
    val body = request.body.asFormUrlEncoded
    val name = body("languageName").head
    request.body.file("attachedLogo").map { tempLogo =>
      val logoName = tempLogo.filename
      val imgData = getBytesFromPath(tempLogo.ref.path)
      (adminManager ? AddImage(logoName, imgData)).mapTo[Unit].map { _ =>
        Ok(Json.toJson("Successfully uploaded"))
      }
      (adminManager ? AddLanguage(Language(None, name, logoName))).mapTo[Int].map { id =>
        Ok("OK")
      }
    }.getOrElse(Future.successful(BadRequest("Error occurred. Please try again")))
  }
  }

  def getLanguage = Action.async {
    (adminManager ? GetLanguage).mapTo[Seq[Language]].map { lang =>
      Ok(Json.toJson(lang))
    }
  }

  def getDirection = Action.async {
    (adminManager ? GetDirection).mapTo[Seq[Direction]].map { dir =>
      Ok(Json.toJson(dir))
    }
  }

  def getRoles = Action.async {
    (adminManager ? GetRoles).mapTo[Seq[Role]].map { role =>
      Ok(Json.toJson(role))
    }
  }

  def createDirection = Action.async(parse.json) { implicit request => {
    val name = (request.body \ "name").as[String]
    (adminManager ? AddDirection(Direction(None, name))).mapTo[Int].map { id =>
      Ok(Json.toJson(id))
    }
  }
  }

  def deleteDirection: Action[JsValue] = Action.async(parse.json) { implicit request => {
    val id = (request.body \ "id").as[String].toInt
    (adminManager ? DeleteDirection(id)).mapTo[Int].map { i =>
      if (i != 0) {
        Ok(Json.toJson(id + " raqamli ism o`chirildi"))
      }
      else {
        Ok("Bunday raqamli ism topilmadi")
      }
    }
  }
  }

  def deleteLanguage: Action[JsValue] = Action.async(parse.json) { implicit request => {
    val id = (request.body \ "id").as[String].toInt
    (adminManager ? DeleteLanguage(id)).mapTo[Int].map { i =>
      if (i != 0) {
        Ok(Json.toJson(id + " raqamli ism o`chirildi"))
      }
      else {
        Ok("Bunday raqamli ism topilmadi")
      }
    }
  }
  }

  private def uploadFile(filename: String, content: Array[Byte]) {
    (adminManager ? AddImage(filename, content)).mapTo[Unit].map { _ =>
      Ok(Json.toJson("Successfully uploaded"))
    }
  }

  def updateDirection: Action[JsValue] = Action.async(parse.json) { implicit request => {
    val id = (request.body \ "id").as[String].toInt
    val name = (request.body \ "name").as[String]
    (adminManager ? UpdateDirection(Direction(Some(id), name))).mapTo[Int].map{ i =>
      if (i != 0){
        Ok(Json.toJson(id + " raqamli ism yangilandi"))
      }
      else {
        Ok("Bunday raqamli ism topilmadi")
      }
    }
  }
  }


  private def getBytesFromPath(filePath: Path): Array[Byte] = {
    Files.readAllBytes(filePath)
  }

}
