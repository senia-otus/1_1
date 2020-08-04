package ru.otus.sc.country.model

case class Country (name: String, capital: String)
case class CountryRequest(countryId: String)
case class CountryResponse(country: Country)