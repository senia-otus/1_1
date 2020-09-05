package ru.otus.sc.auth.model

import ru.otus.sc.common.CommonResponseMixin

case class RegisterResponse(token: TokenView) extends CommonResponseMixin
