package actors

import java.nio.file.{Files, Path, Paths}
import java.util.Date

import akka.actor.Actor
import akka.pattern.pipe
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import dao.{DirectionDao, LanguageDao, RoleDao}
import javax.inject.Inject
import play.api.{Configuration, Environment}
import protocols.AdminProtocol._

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

class AdminManager @Inject()(val environment: Environment,
                             val configuration: Configuration,
                             languageDao: LanguageDao,
                             directionDao: DirectionDao,
                             roleDao: RoleDao,
                            )
                            (implicit val ec: ExecutionContext)
  extends Actor with LazyLogging {

  implicit val defaultTimeout: Timeout = Timeout(60.seconds)
  val config: Configuration = configuration.get[Configuration]("server")
  val imagesPath: String = config.get[String]("images-files")
  val imagesDir: Path = Paths.get(imagesPath)

  def receive = {
    case AddLanguage(data) =>
      addLanguage(data).pipeTo(sender())

    case AddImage(filename, imageData) =>
      addImage(filename, imageData).pipeTo(sender())

    case AddDirection(data) =>
      addDirection(data).pipeTo(sender())

    case GetLanguage =>
      getLanguage.pipeTo(sender())

    case GetDirection =>
      getDirection.pipeTo(sender())

    case DeleteDirection(id) =>
      deleteDirection(id).pipeTo(sender())

    case DeleteLanguage(id) =>
      deleteLanguage(id).pipeTo(sender())

    case GetRoles =>
      readRole.pipeTo(sender())

    case UpdateDirection(data) =>
      updateDirection(data).pipeTo(sender())

    case _ => logger.info(s"received unknown message")
  }

  private def addLanguage(languageData: Language): Future[Int] = {
    languageDao.addLanguage(Language(None, languageData.name, languageData.logoName))
  }

  def addImage(filename: String, imageData: Array[Byte]): Future[Unit] = {
    Future {
      Files.write(imagesDir.resolve(filename), imageData)
    }
  }

  private def addDirection(directionData: Direction): Future[Int] = {
    directionDao.addDirection(Direction(None, directionData.name))
  }

  private def getLanguage = {
    languageDao.getLanguages
  }

  private def getDirection = {
    directionDao.getDirection
  }



  private def deleteDirection(id: Int): Future[Int] = {
    directionDao.deleteDirection(id)
  }

  private def deleteLanguage(id: Int): Future[Int] = {
    for {
      language <- languageDao.findLanguageById(id)
    } yield language match {
      case Some(language) =>
        Files.delete(imagesDir.resolve(language.logoName))
      case None =>

    }
    languageDao.deleteLanguage(id)
  }

  private def readRole: Future[Seq[Role]] = {
    roleDao.getRoles
  }

  private def updateDirection(data: Direction): Future[Int] = {
    directionDao.updateDirection(data)
  }

}
