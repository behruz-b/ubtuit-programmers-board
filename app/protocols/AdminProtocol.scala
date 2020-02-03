package protocols

import play.api.libs.json.{Json, OFormat}

object AdminProtocol {

  case object GetLanguage

  case object GetDirection

  case class AddLanguage(language: Language)

  case class AddDirection(direction: Direction)

  case class DeleteDirection(id: Int)

  case class Language(id: Option[Int] = None,
                      name: String,
                      logoName: String,
                     )

  case class Direction(id: Option[Int] = None,
                       name: String,
                      )

  implicit val workerFormat: OFormat[Language] = Json.format[Language]

  case class AddImage(originalFileName: String, content: Array[Byte])

  implicit val ImageFormat: OFormat[AddImage] = Json.format[AddImage]

  implicit val directionFormat: OFormat[Direction] = Json.format[Direction]


}
