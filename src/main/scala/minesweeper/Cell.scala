package minesweeper

trait Cell {
  private var isHidden = true
  private var isFlag   = false 
  def showCell:     Unit = { this.isHidden = false }
  def toggleFlag:   Unit = { this.isFlag = !this.isFlag }
  def getIsHidden:  Boolean = { this.isHidden }
  def getIsFlag:    Boolean = { this.isFlag }
}

class Mine extends Cell
class Void extends Cell
