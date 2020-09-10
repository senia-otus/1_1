package ru.otus.sc.toys.model

/**
  * Игрушка с именем, ценой и фичами
  *
  * @param name имя
  * @param price цена
  * @param features фичи игрушки
  * @param brokenFeatures сломанные фичи игрушки
  */
case class Toy(
    name: String,
    price: Double = 0.0,
    features: List[String] = List(),
    brokenFeatures: List[String] = List()
) {
  def isBroken: Boolean = brokenFeatures.isEmpty
}
