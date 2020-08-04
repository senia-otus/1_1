package ru.otus.sc.utils.model

/**
  * Util trait for add pretty print method
  *
  * @tparam K data type for keys
  * @tparam V data type for values
  */
trait PrettyPrint[K, V] {
  val info: Map[K, V]
  val prelude: String = "Info contains:"

  /**
    * Print stored data pretty with tabs
    */
  def pretty(): Unit = {
    for {
      k <- info.keys
    } {
      print(s"\t[$k] => ${info(k).toString}")
    }
    println()
  }
}
