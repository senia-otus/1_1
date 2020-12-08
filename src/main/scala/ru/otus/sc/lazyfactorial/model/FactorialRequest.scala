package ru.otus.sc.lazyfactorial.model

/**
 * Request type for calculation factorial
 * @param n number for calculation factorial
 * @param isLazy choose lazy calculation factorial
 */
case class FactorialRequest (n: Int, isLazy: Boolean = false)
