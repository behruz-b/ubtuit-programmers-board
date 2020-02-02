package protocols

import play.api.libs.json.{Json, OFormat}

object AdminProtocol {

  case class AddLanguage(language: Language)

  case class Language(id: Option[Int] = None,
                      name: String,
                      logoName: String,
                     )

  implicit val workerFormat: OFormat[Language] = Json.format[Language]

  case class AddImage(originalFileName: String, content: Array[Byte])


  implicit val ImageFormat: OFormat[AddImage] = Json.format[AddImage]


}
