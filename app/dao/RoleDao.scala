package dao

import com.google.inject.ImplementedBy
import com.typesafe.scalalogging.LazyLogging
import javax.inject.{Inject, Singleton}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import protocols.AdminProtocol._
import slick.jdbc.JdbcProfile
import utils.Date2SqlDate

import scala.concurrent.Future

trait RoleComponent extends {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import utils.PostgresDriver.api._

  class RoleTable(tag: Tag) extends Table[Role](tag, "Role") with Date2SqlDate {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (id.?, name) <> (Role.tupled, Role.unapply _)
  }

}

@ImplementedBy(classOf[RoleDaoImpl])
trait RoleDao {
  def getRoles: Future[Seq[Role]]
}

@Singleton
class RoleDaoImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends RoleDao
    with RoleComponent
    with HasDatabaseConfigProvider[JdbcProfile]
    with LazyLogging {

  import utils.PostgresDriver.api._

  val role = TableQuery[RoleTable]

  override def getRoles: Future[Seq[Role]] = {
    db.run {
      role.result
    }
  }
}

