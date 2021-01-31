package minesweeper

import scala.swing._
import scala.swing.event._

class View(val game: Game, val row: Int, val column: Int) extends Observer {
  game.addObserver(this)
  private val buttons = Stream.range(0, row * column).map(id => new ButtonCell("", id.toString)).seq
  private val boxPanelRows = Stream.range(0, row).map(x => {
    val tmp = new BoxPanel(Orientation.Horizontal)
    Stream.range(0, column).foreach(y => {
      tmp.contents += buttons(y + (x * column))
    })
  tmp
  }).seq
  private val btnReset = new Button("New Game")
  private var view = new MainFrame {
    title = "Minesweeper"
    contents = new BoxPanel(Orientation.Vertical) {
      contents += new BoxPanel(Orientation.Horizontal) { 
        contents += btnReset 
      }
      boxPanelRows.foreach(bpr => contents += bpr)
    }
    size = new Dimension(800, 800)
  }
  def updateView(list: Seq[String]): Unit = {
    if (this.buttons.size == list.size) {
      list.zipWithIndex.foreach {
        case(seq, i) => 
          this.buttons(i).text = seq
          this.buttons(i).default
          if (seq != "" && seq != "F") { this.buttons(i).clicked }
          else if (seq == "F") { this.buttons(i).flag }
      }
    }
  }
  def getResetButton: Button = { return this.btnReset }
  def getButtonsCell: Seq[ButtonCell] = { return this.buttons }
  def show: Unit = { this.view.visible = true }
  def notification: Unit = { updateView(game.getCells) }
}
