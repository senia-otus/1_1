package ru.otus.sc.toys.service

import ru.otus.sc.toys.model.{
  BrokeToyRequest,
  BrokeToyResponse,
  CreateToyRequest,
  RecastToysRequest,
  RecastToysResponse,
  SearchToysRequest,
  SearchToysResponse,
  ShowToysResponse,
  ToyResponse
}

trait ToyService {

  /**
    * Создаем новую игрушку
    *
    * @param request запрос на создание новой игрушки
    * @return цена на новую игрушку
    */
  def createToy(request: CreateToyRequest): ToyResponse

  /**
    * Попытка сломать игрушку
    *
    * @param request
    * @return
    */
  def tryToBroke(request: BrokeToyRequest): BrokeToyResponse
  def searchFeaturedToys(request: SearchToysRequest): SearchToysResponse
  def recastToys(request: RecastToysRequest): RecastToysResponse
  def showAllToys(): ShowToysResponse
}
