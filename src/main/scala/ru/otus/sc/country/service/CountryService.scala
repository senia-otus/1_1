package ru.otus.sc.country.service

import ru.otus.sc.country.dao.CountryDao
import ru.otus.sc.country.model.{Country, CountryRequest, CountryResponse}

trait CountryService {
  def country(request: CountryRequest): CountryResponse
}


case class CountryServiceException private (msg: String)
    extends RuntimeException(s"CountryServiceException: $msg")

object CountryServiceException {
  def forNotExistingId(id: String): CountryServiceException =
    CountryServiceException(s"Country with id: [ $id ] does not exist")
}


class CountryServiceImpl(dao: CountryDao) extends CountryService {

  /**
   * Gets country instance by passed request, or throws a CountryServiceException
   * in case when there is no country with such id
   * @param request: CountryRequest
   * @return Country
   */
  override def country(request: CountryRequest): CountryResponse =
    CountryResponse(
      dao
        .getById(request.countryId)
        .getOrElse(throw CountryServiceException.forNotExistingId(request.countryId))
    )
}

object CountryServiceImpl {
  def apply(dao: CountryDao): CountryServiceImpl = new CountryServiceImpl(dao)
}
