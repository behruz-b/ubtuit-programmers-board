package controllers

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

  def createUser = Action.async(parse.multipartFormData) { implicit request => {
    val body = request.body.asFormUrlEncoded
    val firstName = body("firstName").head
    val fileName =request.body.file("attachedFile").map {tempFile =>
      tempFile.filename
    }
    logger.warn(s"firstName: $firstName")
    Future.successful(Ok("ok"))
  }}

}
