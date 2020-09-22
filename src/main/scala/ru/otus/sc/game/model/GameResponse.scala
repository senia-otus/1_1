package ru.otus.sc.game.model

/**
  * Игровой ответ
  */
trait GameResponse {}

/**
  * Ответ с доступными пунктами меню для текущего игрового состояния
  *
  * @param availableMenuItems доступные пункты меню
  */
case class ShowGameMenuResponse(availableMenuItems: List[String]) extends GameResponse

/**
  * Ответ об успешности регистрации нового игрока
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки)
  */
case class SignUpResponse(success: Boolean, data: String = "") extends GameResponse

/**
  * Ответ об успешности входа в игру
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки)
  */
case class SignInResponse(success: Boolean, data: String = "") extends GameResponse

/**
  * Ответ об успешности входа в игровую карту
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки)
  */
case class EnterGameMapResponse(success: Boolean, data: String = "") extends GameResponse

/**
  * Ответ об успешности загрузки ранее сохраненной игры
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки)
  */
case class LoadGameResponse(success: Boolean, data: String = "") extends GameResponse

/**
  * Ответ об успешности создания новой игры
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки)
  */
case class NewGameResponse(success: Boolean, data: String = "") extends GameResponse

/**
  * Ответ об успешности сохранения игры
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки)
  */
case class SaveGameResponse(success: Boolean, data: String = "") extends GameResponse

/**
  * Ответ о состоянии игровой карты
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки или карта)
  */
case class ShowMapResponse(success: Boolean, data: String = "") extends GameResponse {
  def show(): Unit = println(this.data)
}

/**
  * Ответ о противниках рядом
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки или список противников рядом)
  */
case class ShowEnemiesResponse(success: Boolean, data: String = "") extends GameResponse {
  def show(): Unit = println(this.data)
}

/**
  * Ответ об успешности передвижения в переданном направлении
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки)
  */
case class MoveResponse(success: Boolean, data: String = "") extends GameResponse

/**
  * Ответ об успешности атаки в переданном направлении
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки или лог атаки)
  */
case class AttackDirectionResponse(success: Boolean, data: String = "") extends GameResponse

/**
  * Ответ об успешности выхода из игровой карты
  *
  * @param success успех или провал
  * @param data    сопутствующая информация (текст ошибки)
  */
case class QuitGameMapResponse(success: Boolean, data: String = "") extends GameResponse

/**
  * Отладочная информация. Ответ с текущим игровым состоянием игрока
  *
  * @param data игровое состояние в текстовом виде
  */
case class GameCurrentStateResponse(data: String) extends GameResponse
