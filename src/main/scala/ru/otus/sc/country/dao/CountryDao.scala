package ru.otus.sc.country.dao

import ru.otus.sc.country.model.Country

trait CountryDao {
  /**
   * Gets country by passed id
   * @param id: String
   * @return Option[Country]
   */
  def getById(id: String): Option[Country]
}

class CountryDaoImpl extends CountryDao{
  private[this] val storage: Map[String, Country] =
    Map(
      "RU"  -> Country("Russia", "Moscow"),
      "USA" -> Country("United States Of America", "Washington"),
      "UA"  -> Country("Ukraine", "Kyiv")
    )

  override def getById(id: String): Option[Country] = storage.get(id)
}
