package ru.otus.sc.toys.model

trait ToyRequest {}

case class BrokeToyRequest(name: String) extends ToyRequest
case class CreateToyRequest(name: String, price: Double, features: List[String] = List())
    extends ToyRequest
case class SearchToysRequest() extends ToyRequest
case class RecastToysRequest() extends ToyRequest
