package minesweeper

class Controller(val game: Game, val view: View) {
  var listEvent: List[EventBtn] = List()
  def initController: Unit = {
    game.newGame
    view.show
    view.getButtonsCell.foreach(b => {
      listEvent = new EventBtn(b).addEventListener(game.clickCell, game.clickRightCell) +: listEvent
    })
    listEvent = new EventBtn(view.getResetButton).addEventListener(game.newGame) +: listEvent
  }
}
