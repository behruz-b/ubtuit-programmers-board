package controllers

import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import org.webjars.play.WebJarsUtil
import play.api.mvc._
import views.html._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                                  implicit val webJarsUtil: WebJarsUtil,
                                  indexTemplate: index,
                                  adminTemplate: admin,
                                  registerTemplate: register,
                                  profileTemplate: profile
                              )
                                 (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {

  implicit val defaultTimeout: Timeout = Timeout(60.seconds)

  def index = Action {
    Ok(indexTemplate())
  }

  def admin = Action {
    Ok(adminTemplate())
  }
  def register = Action {
    Ok(registerTemplate())
  }
  def profile = Action {
    Ok(profileTemplate())
  }
}
