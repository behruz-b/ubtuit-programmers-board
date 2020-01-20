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
                                  performersTemplate: performers,
                                  adminTemplate: admin,
                                  registerTemplate: register
                              )
                                 (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {

  implicit val defaultTimeout: Timeout = Timeout(60.seconds)

  def index = Action {
    Ok(indexTemplate())
  }
  def performers = Action {
    Ok(performersTemplate())
  }
  def admin = Action {
    Ok(adminTemplate())
  }
  def register = Action {
    Ok(registerTemplate())
  }
}
