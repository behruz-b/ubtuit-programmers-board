package controllers

import java.nio.file.{Files, Path}

import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import org.webjars.play.WebJarsUtil
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.Json
import play.api.mvc._
import views.html.admin._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.DurationInt

@Singleton
class AdminController @Inject()(val controllerComponents: ControllerComponents,
                                implicit val webJarsUtil: WebJarsUtil,
                                indexTemplate: index,
                              )
                               (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {

  implicit val defaultTimeout: Timeout = Timeout(60.seconds)

  def index = Action {
    Ok(indexTemplate())
  }
  def createUser(): Action[MultipartFormData[TemporaryFile]] = Action.async(parse.multipartFormData) { implicit request: Request[MultipartFormData[TemporaryFile]] => {
    val body = request.body.asFormUrlEncoded
    val firstName = body.get("firstName").flatMap(_.headOption)
    logger.warn(s"name: $firstName")
    request.body.file("attachedFile").map { tempFile =>
      val fileName = tempFile.filename
      val imgData = getBytesFromPath(tempFile.ref.path)
      Redirect(routes.AdminController.index())
        Future.successful(Ok(Json.toJson("Successfully uploaded")))
    }.getOrElse(Future.successful(BadRequest("Error occurred. Please try again")))
  }}

  private def getBytesFromPath(filePath: Path): Array[Byte] = {
    Files.readAllBytes(filePath)
  }

}
