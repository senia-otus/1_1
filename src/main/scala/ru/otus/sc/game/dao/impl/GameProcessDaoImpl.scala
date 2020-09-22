package ru.otus.sc.game.dao.impl

import ru.otus.sc.game.dao.{GameProcessDao, GameStoreDao}
import ru.otus.sc.game.model.GameProcessState._
import ru.otus.sc.game.model._

import scala.collection.mutable
import scala.util.Random

class GameProcessDaoImpl(gsd: GameStoreDao) extends GameProcessDao {
  private val gameState: mutable.Map[String, PlayerCurrentGameState] = mutable.Map.empty
  private val size: Int                                              = GameStats.worldSize
  private val rand                                                   = new Random(size)

  @scala.annotation.tailrec
  private def freeCell(map: Map[Position, GameEntity]): Position = {
    val pos = Position(rand.nextInt(size), rand.nextInt(size))
    if (!map.contains(pos))
      pos
    else
      freeCell(map)
  }

  /**
    * Получение игрового состояния игрока
    *
    * @param player игрок
    * @return игровое состояние игрока
    */
  override def gamerState(player: Player): GameProcessState = {
    this.gameState.get(player.nick) match {
      case Some(playerGame) =>
        playerGame.state
      case None =>
        GameProcessState.NONE
    }
  }

  /**
    * Игрок начинает новую игру.
    * Должна быть создана новая карта и размещены юниты
    *
    * @param player игрок
    */
  override def newGame(player: Player): Boolean = {
    val localMap = GameMap(3)
    val mapWithEnemies = GameMap(
      List
        .range(0, 5)
        .foldLeft(localMap.entities)((acc, _) => {
          acc + (freeCell(acc) -> Player.enemy())
        })
    )
    val pos = freeCell(mapWithEnemies.entities)
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        this.gameState(player.nick) = gameState.copy(
          player = player.copy(health = PlayerStats.maxHealth),
          map = mapWithEnemies.entities,
          state = GameProcessState.MAIN_MENU,
          pos = pos
        )
      case None =>
        this.gameState.put(
          player.nick,
          PlayerCurrentGameState(player, GameProcessState.MAIN_MENU, mapWithEnemies.entities, pos)
        )
    }

    val state = this.gameState(player.nick)
    this.gsd.save(state.player, state.map, state.pos)

    true
  }

  /**
    * Загрузка игры
    *
    * @param player игрок
    * @return смог или не смог
    */
  override def loadGame(player: Player): Boolean = {
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        val savedMap    = this.gsd.loadMap(player)
        val savedPlayer = this.gsd.loadPlayer(player)

        if (savedMap.isDefined && savedPlayer.isDefined) {
          this.gameState(player.nick) = gameState.copy(
            player = savedPlayer.get._1,
            pos = savedPlayer.get._2,
            map = savedMap.get
          )
          true
        } else {
          false
        }
      case None =>
        false
    }
  }

  /**
    * Сохранение игры
    *
    * @param player игрок
    * @return смог или не смог
    */
  override def saveGame(player: Player): Boolean = {
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        this.gsd.savePlayer(gameState.player, gameState.pos) && this.gsd
          .saveMap(gameState.player, gameState.map)
      case None =>
        false
    }
  }

  /**
    * Игрок заходит в игру.
    * Надо сделать поиск сохранения. Если сохранения нет - новая игра
    *
    * @param player игрок
    * @return
    */
  override def signIn(player: Player): Boolean = {
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        this.gameState(player.nick) = gameState.copy(state = GameProcessState.MAIN_MENU)
      case None =>
        this.gameState.put(
          player.nick,
          PlayerCurrentGameState(player, GameProcessState.MAIN_MENU, Map.empty, Position(0, 0))
        )
    }
    true
  }

  /**
    * Игрок заходит на карту.
    * После входа на карту - меняется меню на игровое.
    *
    * @param player игрок
    * @return смог или не смог зайти на карту
    */
  override def enterMap(player: Player): Boolean = {
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        this.gameState(gameState.player.nick) = gameState.copy(state = GameProcessState.GAME)
      case None =>
    }
    true
  }

  /**
    * Показать карту
    *
    * @param player игрок
    * @return карта в виде строки
    */
  override def showMap(player: Player): String = {
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        GameMap(gameState.map).present()
      case None =>
        ""
    }
  }

  /**
    * Показать врагов рядом с позицией игрока
    *
    * @param player игрок
    * @return враги вокруг
    */
  override def showEnemies(player: Player): String = {
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        val map = gameState.map
        val pos = gameState.pos
        s"Your position $pos and enemies: " + Direction.all
          .map { direction =>
            val dPos = pos.byDirection(direction, this.size, this.size)
            if (map.contains(dPos)) {
              map(dPos) match {
                case enemy: Player =>
                  val enemyDesc =
                    s"[$direction] ${enemy.nick} [H-{${enemy.health}}, D-{${enemy.attack}}]"
                  enemyDesc
                case _ =>
                  ""
              }
            } else {
              ""
            }
          }
          .filter(_.nonEmpty)
          .mkString("\n")
      case None =>
        ""
    }
  }

  /**
    * Игрок двигается по карте в направлении
    *
    * @param player    игрок
    * @param direction направление движения
    * @return смог или не смог
    */
  override def move(player: Player, direction: Direction): Boolean = {
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        val newPos = gameState.pos.byDirection(direction, this.size, this.size)
        if (newPos == gameState.pos) {
          true
        } else {
          gameState.map.get(newPos) match {
            case Some(entity) =>
              entity match {
                case _: Player =>
                  false
                case _ =>
                  this.gameState(player.nick) = gameState.copy(pos = newPos)
                  true
              }
            case None =>
              this.gameState(player.nick) = gameState.copy(pos = newPos)
              true
          }
        }
      case None =>
        false
    }
  }

  /**
    * Активировать, если есть, предмет в позиции игрока
    *
    * @param player игрок
    * @return описание того, что произошло с игроком
    */
  override def activateEntity(player: Player): String = {
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        gameState.map.get(gameState.pos) match {
          case Some(entity) =>
            entity.action match {
              case trapDamage: ActionDamageFromTrap =>
                this.gameState(player.nick) = gameState.copy(
                  player = player.copy(health = Math.max(0, player.health - trapDamage.size)),
                  map = gameState.map.removed(gameState.pos)
                )
                trapDamage.toString
              case healthRestore: ActionRestoreHealth =>
                this.gameState(player.nick) = gameState.copy(
                  player = player.copy(health =
                    Math.min(PlayerStats.maxHealth, player.health + healthRestore.size)
                  ),
                  map = gameState.map.removed(gameState.pos)
                )
                healthRestore.toString
              case _ =>
                ""
            }
          case None =>
            ""
        }
      case None =>
        ""
    }
  }

  /**
    * Атаковать в определенном направлении
    *
    * @param player    игрок
    * @param direction направление атаки
    * @return результат атаки
    */
  override def attackDirection(player: Player, direction: Direction): String =
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        val toAttack = gameState.pos.byDirection(direction, this.size, this.size)
        gameState.map.get(toAttack) match {
          case Some(entity) =>
            entity match {
              case enemy: Player =>
                val attackedEnemy = enemy.copy(health = enemy.health - player.attack)
                if (attackedEnemy.health > 0) {
                  val attackedPlayer = player.copy(health = player.health - enemy.attack)
                  if (attackedPlayer.health > 0) {
                    this.gameState(player.nick) = gameState.copy(
                      player = attackedPlayer,
                      map = gameState.map.updated(toAttack, attackedEnemy)
                    )
                    s"""Player attack ${enemy.nick} [D-{${enemy.health - attackedEnemy.health}]
                       |${enemy.nick} attack Player [D-{${player.health - attackedPlayer.health}]""".stripMargin
                  } else {
                    this.gameState(player.nick) = gameState.copy(state = LOSE)
                    "Player is killed. End game"
                  }
                } else {
                  this.gameState(gameState.player.nick) =
                    gameState.copy(map = gameState.map.removed(toAttack))
                  s"Player kill ${enemy.nick} [D-{${enemy.health - attackedEnemy.health}]"
                }
              case _ =>
                "Nothing to attack"
            }
          case None =>
            "Nothing to attack"
        }
      case None =>
        "Nothing to attack"
    }

  /**
    * Выйти из карты в главное меню
    *
    * @param player игрок
    * @return
    */
  override def quitMap(player: Player): Boolean =
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        this.gameState(player.nick) = gameState.copy(state = MAIN_MENU)
        true
      case None =>
        false
    }

  /**
    * Показать текущее состояние игрока
    *
    * @param player игрок
    * @return текущее состояние
    */
  override def showCurrentState(player: Player): String = {
    this.gameState.get(player.nick) match {
      case Some(gameState) =>
        gameState.toString
      case None =>
        "NOTHING STATE"
    }
  }
}
