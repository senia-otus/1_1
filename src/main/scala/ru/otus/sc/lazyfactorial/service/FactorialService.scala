package ru.otus.sc.lazyfactorial.service

import ru.otus.sc.lazyfactorial.model.{FactorialRequest, FactorialResponse}

/**
 * Service for calculation factorial
 */
trait FactorialService {
  def calcFactorial(request: FactorialRequest): FactorialResponse
}
