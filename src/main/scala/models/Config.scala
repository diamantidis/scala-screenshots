package models

final case class Config(
  val host: String,
  val urls: List[String],
  val dimensions: List[ScreenDimension]
)
