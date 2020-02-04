package dao

import com.google.inject.ImplementedBy
import com.typesafe.scalalogging.LazyLogging
import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import protocols.AdminProtocol._
import slick.jdbc.JdbcProfile
import utils.Date2SqlDate

import scala.concurrent.Future

trait LanguageComponent extends {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import utils.PostgresDriver.api._

  class LanguageTable(tag: Tag) extends Table[Language](tag, "Language") with Date2SqlDate {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def logoName = column[String]("logo")

    def * = (id.?, name, logoName) <> (Language.tupled, Language.unapply _)
  }

}

@ImplementedBy(classOf[LanguageDaoImpl])
trait LanguageDao {
  def addLanguage(languageData: Language): Future[Int]

  def getLanguages: Future[Seq[Language]]

  def deleteLanguage(id: Int): Future[Int]
}

@Singleton
class LanguageDaoImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends LanguageDao
    with LanguageComponent
    with HasDatabaseConfigProvider[JdbcProfile]
    with LazyLogging {

  import utils.PostgresDriver.api._

  val language = TableQuery[LanguageTable]

  override def addLanguage(languageData: Language): Future[Int] = {
    db.run {
      (language returning language.map(_.id)) += languageData
    }
  }

  override def getLanguages: Future[Seq[Language]] = {
    db.run {
      language.result
    }
  }

  override def deleteLanguage(id: Int): Future[Int] = {
    db.run{
      language.filter(_.id === id).delete
    }
  }

}

