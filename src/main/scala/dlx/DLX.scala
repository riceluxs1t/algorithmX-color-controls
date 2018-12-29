package dlx

import scala.collection.mutable.ListBuffer

object DLX {
  def makeHeadFromMatrix(inputMatrix: Array[Array[Int]]): ColumnObject = {
    val head: ColumnObject = ColumnObject.makeHead()

    inputMatrix(0).indices.foreach(
      columnIndex => {
        val newColumn: ColumnObject = ColumnObject.makeColumn(
          isPrimary = true,
          N = Option(columnIndex)
        )
        appendHorizontally(newColumn.data, head.data)
      }
    )

    for (rowIndex <- inputMatrix.indices) {
      var (currentColumnHead, currentRowOfDataObjects) = (head.R, ListBuffer[DataObject]())

      for (columnIndex <- inputMatrix(rowIndex).indices) {
        if (inputMatrix(rowIndex)(columnIndex) != 0) {
          val newDataObject = DataObject.newLinkedListNode(Option(rowIndex), currentColumnHead.C)

          appendVertically(newDataObject, currentColumnHead)
          currentRowOfDataObjects :+= newDataObject
        }
        currentColumnHead = currentColumnHead.R
      }

      val currentRowHead = currentRowOfDataObjects.head

      currentRowOfDataObjects.slice(1, currentRowOfDataObjects.length).foreach(
        eachData => appendHorizontally(eachData, currentRowHead)
      )
    }

    head
  }

  def chooseColumnWithMinimumS(head: ColumnObject): ColumnObject = {
    var curColumnData: DataObject = head.R
    var chosenColumn: ColumnObject = head.R.C

    while (curColumnData.R != head.data) {
      curColumnData = curColumnData.R
      chosenColumn = if (curColumnData.C.isPrimary && curColumnData.C.S < chosenColumn.S) curColumnData.C else chosenColumn
    }
    chosenColumn
  }

  def coverColumn(c: ColumnObject): Unit = {
    require(c.isPrimary)

    c.data.R.L = c.L
    c.data.L.R = c.R

    var i = c.D
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
    var i = c.U
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
    head.R == head.data
  }

  def search(currentSolution: ListBuffer[DataObject], foundSolutions: ListBuffer[List[Int]], head: ColumnObject): Unit = {
    if (isSolutionFound(head)) {
      foundSolutions += currentSolution.map(_.optionId.get).toList
    }

    val columnChosen = chooseColumnWithMinimumS(head)

    if (columnChosen.S > 0) {
      coverColumn(columnChosen)

      var r = columnChosen.D
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

  def solve(head: ColumnObject): List[List[Int]] = {
    val solution = new ListBuffer[DataObject]
    val foundSolutions = new ListBuffer[List[Int]]
    search(solution, foundSolutions, head)
    foundSolutions.toList
  }

  private def appendHorizontally(that: DataObject, head: DataObject): Unit = {
    that.L = head.L
    that.R = head

    head.L.R = that
    head.L = that
  }

  private def appendVertically(that: DataObject, head: DataObject): Unit = {
    that.U = head.U
    that.D = head

    head.U.D = that
    head.U = that
    head.C.S += 1
  }
}