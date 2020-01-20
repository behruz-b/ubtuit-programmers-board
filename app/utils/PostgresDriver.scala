package utils

import com.github.tminglei.slickpg._

trait PostgresDriver extends ExPostgresProfile
    with PgPlayJsonSupport with PgArraySupport {
  def pgjson = "jsonb" // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"

  override val api = MyAPI

  object MyAPI extends API with JsonImplicits with ArrayImplicits {}
}

object PostgresDriver extends PostgresDriver
