package ru.otus.sc.custom.model

trait ShowCount {}

case class ShowCountMethodsRequest()                 extends ShowCount
case class ShowCountMethodsResponse(methods: String) extends ShowCount

case class ShowCountsRequest(method: String) extends ShowCount

trait ShowCountsResponse {
  def isFail: Boolean
  def count: Int
}
case class ShowCountsResponseSuccess(count: Int) extends ShowCount with ShowCountsResponse {
  val isFail = false
}
case class ShowCountsResponseFail(exception: String) extends ShowCount with ShowCountsResponse {
  val isFail = true
  val count  = 0
}

case class IncMethodRequest(method: String)    extends ShowCount
case class IncMethodResponse(success: Boolean) extends ShowCount
