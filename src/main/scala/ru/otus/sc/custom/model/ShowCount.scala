package ru.otus.sc.custom.model

trait ShowCount {}

case class ShowCountMethodsRequest()                 extends ShowCount
case class ShowCountMethodsResponse(methods: String) extends ShowCount

case class ShowCountsRequest(method: String) extends ShowCount

trait ShowCountsResponse {}
case class ShowCountsResponseSuccess(count: Int)     extends ShowCount with ShowCountsResponse
case class ShowCountsResponseFail(exception: String) extends ShowCount with ShowCountsResponse

case class IncMethodRequest(method: String)    extends ShowCount
case class IncMethodResponse(success: Boolean) extends ShowCount
