package ru.otus.sc.lazyfactorial.model

/**
 * Response type for factorail result
 * @param factorialResult result value
 * @param errorMessage exception text
 */
case class FactorialResponse (factorialResult: BigInt, errorMessage: String="")
