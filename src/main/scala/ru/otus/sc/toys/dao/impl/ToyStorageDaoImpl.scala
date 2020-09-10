package ru.otus.sc.toys.dao.impl

import ru.otus.sc.toys.dao.ToyStorageDao
import ru.otus.sc.toys.model.Toy

import scala.util.Random

class ToyStorageDaoImpl(initToys: List[Toy]) extends ToyStorageDao {
  private var _toys: List[Toy] = initToys
  private val rand             = new Random(100)

  /**
    * Список всех игрушек
    *
    * @return список игрушек
    */
  def toys(): List[Toy] = this._toys

  /**
    * Добавление новой игрушки
    *
    * @param toy новая игрушка
    */
  def newToy(toy: Toy): Unit = this._toys = toy :: this._toys

  /**
    * Добавление коллекции игрушек
    *
    * @param toys коллекция игрушек
    */
  def newToys(toys: List[Toy]): Unit = this._toys = toys ::: this._toys

  /**
    * Попытка сломать игрушку.
    * Если удалось, то игрушка будет сломанной.
    * Иначе, игрушка остается в целости и сохранности
    *
    * @param toy игрушка для разламывания
    * @return да или нет
    */
  def brokeToy(toy: Toy): Boolean =
    if (this.rand.nextInt() > 30)
      false
    else {

      true
    }

  /**
    * Поиск игрушки по ее фичам
    *
    * @param filter фильтр игрушки
    * @return список найденных игрушек
    */
  def searchToyByFeature(filter: List[String] => Boolean): List[Toy] =
    this._toys.filter(t => filter(t.features))

  /**
    * Переделка выбранных игрушек с целью создания новых / новой
    *
    * @return список новых игрушек
    */
  def recastToys(toysTo: List[Toy]): List[Toy] = {
    toysTo.flatMap(t => {
      if (this.brokeToy(t)) {
        List()
      } else if (this.rand.nextInt() < 30) {
        List(t)
      } else if (this.rand.nextInt() < 70 && t.features.size > 1) {
        val count = this.rand.nextInt(t.features.size - 2) + 1
        val (features1, features2) =
          t.features.foldLeft((List[String](), List[String]()))((acc, feature) => {
            if (acc._1.size < count) {
              (feature :: acc._1, acc._2)
            } else {
              (acc._1, feature :: acc._2)
            }
          })
        List(
          Toy(s"${t.name}1", t.price * (count / t.features.size), features1),
          Toy(s"${t.name}2", t.price * (t.features.size - count / t.features.size), features2)
        )
      } else {
        List(Toy(t.name, t.price * 2, t.features))
      }
    })
  }
}
