package minesweeper

trait Observable {
  var listObserver: List[Observer] = List()

  def addObserver(observer: Observer): Unit = {
    listObserver = observer +: listObserver
  }

  def removeObserver(observer: Observer): Unit = {
    listObserver = listObserver.filterNot(obs => obs == observer)
  }

  def update(): Unit = {
    listObserver.foreach(observer => observer.notification)
  }
}

