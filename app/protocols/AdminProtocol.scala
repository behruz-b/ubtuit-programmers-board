package protocols

import play.api.libs.json.{Json, OFormat}

object AdminProtocol {

  case object GetLanguage

  case object GetDirection

  case object GetRoles

  case class AddLanguage(language: Language)

  case class AddDirection(direction: Direction)

  case class DeleteDirection(id: Int)

  case class DeleteLanguage(id: Int)

  case class UpdateDirection(updateDirection: Direction)

  case class UpdateLanguage(updateLanguage: Language)

  case class Language(id: Option[Int] = None,
                      name: String,
                      logoName: String
                     )


  implicit val languageFormat: OFormat[Language] = Json.format[Language]

  case class AddImage(originalFileName: String, content: Array[Byte])

  implicit val ImageFormat: OFormat[AddImage] = Json.format[AddImage]

  case class Direction(id: Option[Int] = None,
                       name: String
                      )
  implicit val directionFormat: OFormat[Direction] = Json.format[Direction]

  case class Role(id: Option[Int] = None,
                       name: String
                      )
  implicit val roleFormat: OFormat[Role] = Json.format[Role]


}
