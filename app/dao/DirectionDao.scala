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

  class DirectionTable(tag: Tag) extends Table[Direction](tag, "Direction") with Date2SqlDate {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id.?, name) <> (Direction.tupled, Direction.unapply _)
  }

}

@ImplementedBy(classOf[LanguageDaoImpl])
trait DirectionDao {
  def addDirection(directionData: Direction): Future[Int]
}

@Singleton
class DirectionDaoImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends DirectionDao
    with DirectionComponent
    with HasDatabaseConfigProvider[JdbcProfile]
    with LazyLogging {

  import utils.PostgresDriver.api._

  val direction = TableQuery[DIrectionTable]

  override def addDirection(directionData: Direction): Future[Int] = {
    db.run {
      (direction returning direction.map(_.id)) += directionData
    }
  }
}

