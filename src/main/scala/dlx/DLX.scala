package dlx

import scala.collection.mutable.ListBuffer

object DLX {
  def chooseColumnWithMinimumS(head: ColumnObject): ColumnObject = {
    var curColumn: ColumnObject = head
    var chosenColumn: ColumnObject = head

    while (curColumn.R != head) {
      curColumn = curColumn.R.asInstanceOf[ColumnObject]
      chosenColumn = if (curColumn.isPrimary && curColumn.S < chosenColumn.S) curColumn else chosenColumn
    }
    chosenColumn
  }

  def chooseColumnNaive(head: ColumnObject): ColumnObject = head.R.asInstanceOf[ColumnObject]

  def coverColumn(c: ColumnObject): Unit = {
    require(c.isPrimary)

    c.R.L = c.L
    c.L.R = c.R

    var i = c.D
    while (i != c) {
      var j = i.R
      while (j != i) {
        j.D.U = j.U
        j.U.D = j.D
        j.C.asInstanceOf[ColumnObject].S -= 1

        j = j.R
      }
      i = i.D
    }
  }

  def uncoverColumn(c: ColumnObject): Unit = {
    var i = c.U
    while (i != c) {
      var j = i.L
      while (j != i) {
        j.C.asInstanceOf[ColumnObject].S += 1
        j.D.U = j
        j.U.D = j

        j = j.L
      }

      i = i.U
    }

    c.R.L = c
    c.L.R = c
  }

  def isSolutionFound(head: ColumnObject): Boolean = {
    head.R == head
  }

  def search(solution: ListBuffer[DataObject], head: ColumnObject): Unit = {
    if (isSolutionFound(head)) {
      printSolution(solution)
    }

    val columnChosen = chooseColumnWithMinimumS(head)
    if (columnChosen.S > 0) {
      coverColumn(columnChosen)

      var r = columnChosen.D
      while (r != columnChosen) {
        solution += r

        var j = r.R
        while (j != r) {
          coverColumn(j.C.asInstanceOf[ColumnObject])
          j = j.R
        }

        search(solution, head)

        solution -= r
        var c = r.C
        j = r.L
        while (j != r) {
          uncoverColumn(j.C.asInstanceOf[ColumnObject])
          j = j.L
        }

        r = r.D
      }
    }

    uncoverColumn(columnChosen)
  }

  def printSolution(solution: ListBuffer[DataObject]): Unit = {
    System.out.println("new solution found.")
    for (dataObject <- solution.toList) {
      System.out.println(dataObject.C.asInstanceOf[ColumnObject].N)
    }
  }

  def solve(head: ColumnObject): Unit = {
    val solution = new ListBuffer[DataObject]
    search(solution, head)
  }
}