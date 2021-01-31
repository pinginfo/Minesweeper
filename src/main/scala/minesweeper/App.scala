package minesweeper

object Minesweeper extends App {
  val game = new Game(15, 15, 45)
  val view = new View(game, 15, 15)
  val controller = new Controller(game, view)

  controller.initController
}
