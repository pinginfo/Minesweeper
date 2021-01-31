package minesweeper

import scala.swing._
import scala.swing.event._

class EventBtn(val button: Button) extends Publisher {
  def addEventListener(left: (Int) => Unit, right: (Int) => Unit): EventBtn = {
    listenTo(this.button.mouse.clicks)
    reactions += {
      case e: MouseClicked => {
        if (e.modifiers == 0) { left(e.source.name.toInt) }
        else if (e.modifiers == 256) { right(e.source.name.toInt) }
      }
    }
    this
  }
  def addEventListener(callback: () => Unit): EventBtn = {
    listenTo(this.button.mouse.clicks)
    reactions += {
      case e: MouseClicked => {
        callback()
      }
    }
    this
  }
}
