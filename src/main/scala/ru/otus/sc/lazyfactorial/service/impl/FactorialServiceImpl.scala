package ru.otus.sc.lazyfactorial.service.impl

import ru.otus.sc.lazyfactorial.dao.FactorialDao
import ru.otus.sc.lazyfactorial.model.{FactorialRequest, FactorialResponse}
import ru.otus.sc.lazyfactorial.service.FactorialService

/**
  * Implementation service for calculation of factorial with lazy option
  * @param dao calculation of factorial
  * @see FactorialService
  */
class FactorialServiceImpl(dao: FactorialDao) extends FactorialService {

  /**
    * Calculation factorial and handle any exceptions
    * @param request Request type with value and lazy flag option
    * @return Response result calculation type
    * @see FactorialRequest, FactorialResponse
    */
  override def calcFactorial(request: FactorialRequest): FactorialResponse = {
    lazy val factLazy = dao.fact(request.n)
    try {
      require(request.n > 0)
      if (!request.isLazy) {
        val result = dao.fact(request.n)
        FactorialResponse(result)
      } else FactorialResponse(factLazy)
    } catch {
      case e: IllegalArgumentException =>
        FactorialResponse(-1, s"Invalid parameter n=[${request.n}] must be positive number.")
      case e: StackOverflowError =>
        FactorialResponse(-1, "Ooops! To much factorial result value. Stack overflow!")
    }

  }
}
