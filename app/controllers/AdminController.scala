package controllers

import java.nio.file.{Files, Path}

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import org.webjars.play.WebJarsUtil
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.Json
import play.api.mvc._
import protocols.AdminProtocol.{AddImage, AddLanguage, Language}
import views.html.admin._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.DurationInt

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
    logger.warn(s"name: $firstName")
    request.body.file("attachedFile").map { tempFile =>
      val fileName = tempFile.filename
      val imgData = getBytesFromPath(tempFile.ref.path)
      logger.warn(s"name: $fileName")
      Future.successful(Ok("OK"))
    }.getOrElse(Future.successful(BadRequest("Error occurred. Please try again")))
  }}

  def createLanguage(): Action[MultipartFormData[TemporaryFile]] = Action.async(parse.multipartFormData) { implicit request: Request[MultipartFormData[TemporaryFile]] => {
    val body = request.body.asFormUrlEncoded
    val name = body("languageName").head
    logger.warn(s"name: $name")
    request.body.file("attachedLogo").map { tempLogo =>
      val logoName = tempLogo.filename
      val imgData = getBytesFromPath(tempLogo.ref.path)
      logger.warn(s"name: $logoName")
      uploadFile(logoName, imgData)
      (adminManager ? AddLanguage(Language(None, name, logoName))).mapTo[Int].map{ id =>
        Ok("OK")
      }
    }.getOrElse(Future.successful(BadRequest("Error occurred. Please try again")))
  }
  }


  private def uploadFile(filename: String, content: Array[Byte]) {
      (adminManager ? AddImage(filename, content)).mapTo[Unit].map { _ =>
        Ok(Json.toJson("Successfully uploaded"))
      }
  }

  private def getBytesFromPath(filePath: Path): Array[Byte] = {
    Files.readAllBytes(filePath)
  }

}
