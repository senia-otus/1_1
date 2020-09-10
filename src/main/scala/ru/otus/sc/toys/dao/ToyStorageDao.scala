package ru.otus.sc.toys.dao

import ru.otus.sc.toys.model.Toy

trait ToyStorageDao {

  /**
    * Список всех игрушек
    *
    * @return список игрушек
    */
  def toys(): List[Toy]

  /**
    * Добавление новой игрушки
    *
    * @param toy новая игрушка
    */
  def newToy(toy: Toy): Unit

  /**
    * Добавление коллекции игрушек
    *
    * @param toys коллекция игрушек
    */
  def newToys(toys: List[Toy]): Unit

  /**
    * Попытка сломать игрушку.
    * Если удалось, то игрушка будет сломанной.
    * Иначе, игрушка остается в целости и сохранности
    *
    * @param toy игрушка для разламывания
    * @return да или нет
    */
  def brokeToy(toy: Toy): Boolean

  /**
    * Поиск игрушки по ее фичам
    *
    * @param filter фильтр игрушки
    * @return список найденных игрушек
    */
  def searchToyByFeature(filter: List[String] => Boolean): List[Toy]

  /**
    * Переделка выбранных игрушек с целью создания новых / новой
    *
    * @return список новых игрушек
    */
  def recastToys(toysTo: List[Toy] = List()): List[Toy]
}
