package dlx

import scala.collection.mutable.ListBuffer

object DLX {
  def makeHeadFromMatrix(inputMatrix: Array[Array[Int]]): ColumnObject = {
    val head: ColumnObject = ColumnObject.makeHead()

    val headData = DataObject.newLinkedListNode(-1)
    headData.C = head
    head.data = headData

    for (columnIndex <- inputMatrix(0).indices) {
      val newColumn: ColumnObject = ColumnObject.makeNewPrimaryColumn(columnIndex)
      newColumn.data.L = head.data.L
      newColumn.data.R = head.data
      head.data.L.R = newColumn.data
      head.data.L = newColumn.data
    }

    for (rowIndex <- inputMatrix.indices) {
      var (currentColumnData, dataRow) = (head.data.R, Array[DataObject]())

      for (columnIndex <- inputMatrix(rowIndex).indices) {
        if (inputMatrix(rowIndex)(columnIndex) != 0) {
          val newDataObject = DataObject.newLinkedListNode(rowIndex)
          newDataObject.U = currentColumnData.U
          newDataObject.D = currentColumnData
          newDataObject.C = currentColumnData.C
          currentColumnData.U.D = newDataObject
          currentColumnData.U = newDataObject
          currentColumnData.C.S += 1
          dataRow :+= newDataObject
        }
        currentColumnData = currentColumnData.R
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
    var curColumnData: DataObject = head.data.R
    var chosenColumn: ColumnObject = head.data.R.C

    while (curColumnData.R != head.data) {
      curColumnData = curColumnData.R
      chosenColumn = if (curColumnData.C.isPrimary && curColumnData.C.S < chosenColumn.S) curColumnData.C else chosenColumn
    }
    chosenColumn
  }

  def coverColumn(c: ColumnObject): Unit = {
    require(c.isPrimary)

    c.data.R.L = c.data.L
    c.data.L.R = c.data.R

    var i = c.data.D
    while (i != c.data) {
      var j = i.R
      while (j != i) {
        j.D.U = j.U
        j.U.D = j.D
        j.C.S -= 1

        j = j.R
      }
      i = i.D
    }
  }

  def uncoverColumn(c: ColumnObject): Unit = {
    var i = c.data.U
    while (i != c.data) {
      var j = i.L
      while (j != i) {
        j.C.S += 1
        j.D.U = j
        j.U.D = j

        j = j.L
      }

      i = i.U
    }

    c.data.R.L = c.data
    c.data.L.R = c.data
  }

  def isSolutionFound(head: ColumnObject): Boolean = {
    head.data.R == head.data
  }

  def search(currentSolution: ListBuffer[DataObject], foundSolutions: ListBuffer[List[Int]], head: ColumnObject): Unit = {
    if (isSolutionFound(head)) {
      foundSolutions += currentSolution.map(_.optionId).toList
    }

    val columnChosen = chooseColumnWithMinimumS(head)

    if (columnChosen.S > 0) {
      coverColumn(columnChosen)

      var r = columnChosen.data.D
      while (r != columnChosen.data) {
        currentSolution += r

        var j = r.R
        while (j != r) {
          coverColumn(j.C)
          j = j.R
        }

        search(currentSolution, foundSolutions, head)

        currentSolution -= r
        var c = r.C
        j = r.L
        while (j != r) {
          uncoverColumn(j.C)
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