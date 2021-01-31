package minesweeper

import scala.util.Random

class Game(val row: Int, val column: Int, val nbMine: Int) extends Observable {
  private var grid: Seq[Cell] = generateCells 

  private def generateCells: Seq[Cell] = {
    Stream
      .range(0, this.row * this.column)
      .map(_ => {
        val rdm = new Random().nextInt((this.row * this.column) / this.nbMine)
        if (rdm == 1) new Mine else new Void
      }).seq
  }

  def newGame(): Unit = {
    this.grid = generateCells 
    update
  }

  def getCells: Seq[String] = {
    grid.map(cell => {
      cell match {
        case m: Mine => if (!cell.getIsHidden) { "X" } else if (cell.getIsFlag) { "F" } else { "" } 
        case v: Void => if (!cell.getIsHidden) { 
          getNeighbour(this.grid.indexOf(cell))
            .filter(neighbour => neighbour.isInstanceOf[Mine])
            .size
            .toString
        } else if (cell.getIsFlag) { "F" } else { "" }
      }
    }).seq
  }

  def clickCell(id: Int): Unit = {
    if (id < this.grid.size && id >= 0 && !this.grid(id).getIsFlag) {
      if (this.grid(id).getIsHidden) {
        this.grid(id).showCell
        this.autoClean(id)
      } else {
        val sumMine: Int = getNeighbour(id).filter(n => n.isInstanceOf[Mine]).size
        val sumFlag: Int = getNeighbour(id).filter(n => n.getIsFlag).size
        if (sumMine == sumFlag) {
          getNeighbour(id).filter(n => n.getIsHidden).foreach(c => clickCell(this.grid.indexOf(c)))
        }
      }
      update
    }
  }

  def clickRightCell(id: Int): Unit = {
    if (id < this.grid.size && id >= 0) {
      this.grid(id).toggleFlag
      update
    }
  }

  private def autoClean(id: Int): Unit = {
    this.grid(id) match {
      case m: Mine =>
      case v: Void => {
        val neighbour = getNeighbour(id)
        if (neighbour.filter(n => n.isInstanceOf[Mine]).size == 0) {
          neighbour.filter(c => c.getIsHidden).foreach(c => {
            c.showCell
            val tmp = getNeighbour(this.grid.indexOf(c)).filter(n => n.isInstanceOf[Mine]).size
            if (tmp == 0) {
              autoClean(this.grid.indexOf(c))
            }
          })
        }
      }
    }
  }

  private def getNeighbour(id: Int): Seq[Cell] = {
    val coordinates = getCellById(id)
    val column = coordinates._1
    val row = coordinates._2
    Seq(
      getIdCell(column - 1, row - 1),
      getIdCell(column, row - 1),
      getIdCell(column + 1, row - 1),
      getIdCell(column - 1, row),
      getIdCell(column + 1, row),
      getIdCell(column - 1, row + 1),
      getIdCell(column, row + 1),
      getIdCell(column + 1, row + 1)
    ).filter(id => id >= 0 && id < this.row * this.column)
      .map(id => this.grid(id))
  }

  private def getIdCell(c: Int, r: Int): Int = {
    if (c< 0 || c>= this.column || r< 0 || r>= this.row) -1
    else c + (r* this.row)
  }

  private def getCellById(id: Int): Tuple2[Int, Int] = (id % this.row, id / this.row)
}
