package dlx

import scala.collection.mutable.ListBuffer

object DLX {
  def makeHeadFromMatrix(inputMatrix: Array[Array[Int]]): ColumnObject = {
    val head: ColumnObject = new ColumnObject(false, 0, -1, null, null, null, null, null, -1)
    head.L = head
    head.R = head
    head.U = head
    head.D = head

    for (columnIndex <- inputMatrix(0).indices) {
      val col: ColumnObject = new ColumnObject(true, columnIndex, 0, null, null, null, null, null, -1)
      col.L = head.L
      col.R = head
      col.U = col
      col.D = col
      head.L.R = col
      head.L = col
    }

    for (rowIndex <- inputMatrix.indices) {
      var (currentColumn, dataRow) = (head.R, Array[DataObject]())

      for (columnIndex <- inputMatrix(rowIndex).indices) {
        if (inputMatrix(rowIndex)(columnIndex) != 0) {
          val newDataObject = new DataObject(null, null, null, null, null, rowIndex)
          newDataObject.L = newDataObject
          newDataObject.R = newDataObject
          newDataObject.U = currentColumn.U
          newDataObject.D = currentColumn
          newDataObject.C = currentColumn
          currentColumn.U.D = newDataObject
          currentColumn.U = newDataObject

          currentColumn.asInstanceOf[ColumnObject].S += 1
          dataRow :+= newDataObject
        }
        currentColumn = currentColumn.R
      }

      if (dataRow.length > 0) {
        val rowHead = dataRow(0)
        for (columnIndex <- 1 until dataRow.length) {
          dataRow(columnIndex).L = rowHead.L
          dataRow(columnIndex).R = rowHead
          rowHead.L.R = dataRow(columnIndex)
          rowHead.L = dataRow(columnIndex)
        }
      }
    }

    head
  }

  def chooseColumnWithMinimumS(head: ColumnObject): ColumnObject = {
    var curColumn: ColumnObject = head.R.asInstanceOf[ColumnObject]
    var chosenColumn: ColumnObject = head.R.asInstanceOf[ColumnObject]

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

  def search(currentSolution: ListBuffer[DataObject], foundSolutions: ListBuffer[List[Int]], head: ColumnObject): Unit = {
    if (isSolutionFound(head)) {
      foundSolutions += currentSolution.map(_.optionId).toList
    }

    val columnChosen = chooseColumnWithMinimumS(head)

    if (columnChosen.S > 0) {
      coverColumn(columnChosen)

      var r = columnChosen.D
      while (r != columnChosen) {
        currentSolution += r

        var j = r.R
        while (j != r) {
          coverColumn(j.C.asInstanceOf[ColumnObject])
          j = j.R
        }

        search(currentSolution, foundSolutions, head)

        currentSolution -= r
        var c = r.C
        j = r.L
        while (j != r) {
          uncoverColumn(j.C.asInstanceOf[ColumnObject])
          j = j.L
        }

        r = r.D
      }

      uncoverColumn(columnChosen)
    }
  }

  def printSolution(solution: ListBuffer[Int]): Unit = {
    System.out.println("new solution found.")
    for (optionId <- solution.toList) {
      System.out.println(optionId)
    }
  }

  def solve(head: ColumnObject): List[List[Int]] = {
    val solution = new ListBuffer[DataObject]
    val foundSolutions = new ListBuffer[List[Int]]
    search(solution, foundSolutions, head)
    foundSolutions.toList
  }
}