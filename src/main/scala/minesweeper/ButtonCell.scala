package minesweeper

import scala.swing._
import scala.swing.UIElement._
import java.awt.Color

class ButtonCell(val text0: String, val name0: String) extends Button {
  this.name = name0
  this.maximumSize = new Dimension(50, 50)
  this.minimumSize = new Dimension(50, 50)

  def default: Unit = { this.background = new Color(219, 219, 219) }
  def clicked: Unit = { this.background = new Color(249, 249, 249) }
  def flag:    Unit = { this.background = new Color(200, 200, 200) }
}
