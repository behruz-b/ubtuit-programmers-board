package actors

import java.nio.file.{Files, Path, Paths}
import java.util.Date

import akka.actor.Actor
import akka.pattern.pipe
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import dao.LanguageDao
import javax.inject.Inject
import play.api.{Configuration, Environment}
import protocols.AdminProtocol._

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

class AdminManager @Inject()(val environment: Environment,
                             val configuration: Configuration,
                             languageDao: LanguageDao
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

    case _ => logger.info(s"received unknown message")
  }

  private def addLanguage(languageData: Language): Future[Int] = {
    languageDao.addLanguage(Language(None, languageData.name, filenameGenerator()))
  }

  def addImage(filename: String, imageData: Array[Byte]): Future[Unit]  = {
    Future {
      Files.write(imagesDir.resolve(filenameGenerator()), imageData)
    }
  }
  private def filenameGenerator() = {
    new Date().getTime.toString + ".png"
  }
//  def getImage(fileId: String) = {
//    Future {
//      require(isCorrectFileName(fileId))
//      Files.readAllBytes(imagesDir.resolve(fileId + ".png"))
//    }
//  }
//
//  def getImages = {
//    Future {
//      new File(imagesDir.toString)
//        .listFiles
//        .filter(_.isFile)
//        .map(_.getName.split('.')(0))
//        .toList
//    }
//  }
//  def isCorrectFileName(name: String) = {
//    badCharsR.findFirstIn(name).isEmpty
//  }
//
//  private val badCharsR = """\/|\.\.|\?|\*|:|\\""".r // / .. ? * : \
}
