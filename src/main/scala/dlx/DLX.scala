package dlx

import scala.collection.mutable.ListBuffer

object DLX {
  def solve(head: ColumnObject): Set[Set[Int]] = {
    val solution = new ListBuffer[DataObject]
    val foundSolutions = new ListBuffer[List[Int]]
    search(solution, foundSolutions, head)
    foundSolutions.toList.map(_.toSet).toSet
  }

  private def search(currentSolution: ListBuffer[DataObject], foundSolutions: ListBuffer[List[Int]], head: ColumnObject): Unit = {
    val selectOption = doWhile(
      getNext = data => data.R,
      body = data => coverColumn(data.C)
    ) _

    val deselectOption = doWhile(
      getNext = data => data.L,
      body = data => uncoverColumn(data.C)
    ) _

    if (isSolutionFound(head)) {
      foundSolutions += currentSolution.map(_.optionId.get).toList
    } else {
      val columnChosen = chooseColumnWithMinimumS(head)
      coverColumn(columnChosen)

      val iterateOverColumn = doWhile(
        getNext = data => data.D,
        body = data => {
          currentSolution += data
          selectOption(data.R, _ != data)

          search(currentSolution, foundSolutions, head)

          currentSolution -= data
          deselectOption(data.L, _ != data)
        }
      ) _

      val startData = columnChosen.D
      val endData = columnChosen.data

      iterateOverColumn(startData, _ != endData)

      uncoverColumn(columnChosen)
    }
  }

  private def doWhile(
    getNext: DataObject => DataObject,
    body: DataObject => Unit
  )(start: DataObject, cond: DataObject => Boolean): Unit = {
    for (data <- Stream.iterate(start)(getNext).takeWhile(cond)) {
      body(data)
    }
  }

  private def isSolutionFound(head: ColumnObject): Boolean = {
    head.R == head.data
  }

  private[dlx] def chooseColumnWithMinimumS(head: ColumnObject): ColumnObject = {
    var chosenColumn: ColumnObject = head.R.C

    doWhile(
      getNext = data => data.R,
      body = data => {
        chosenColumn = if (data.C.isPrimary && data.C.S < chosenColumn.S) data.C else chosenColumn
      }
    )(head.R, _ != head.data)

    chosenColumn
  }

  private def coverColumn(c: ColumnObject): Unit = {
    def removeColumnFromHeaderList(): Unit = {
      c.data.R.L = c.L
      c.data.L.R = c.R
    }

    def removeDataObjectFromColumn(dataObject: DataObject): Unit = {
      dataObject.D.U = dataObject.U
      dataObject.U.D = dataObject.D
      dataObject.C.S -= 1
    }

    removeColumnFromHeaderList()

    val startOption = c.D
    doWhile(
      getNext = _.D,
      body = i => {
        val j = i.R
        doWhile(
          getNext = data => data.R,
          body = data => removeDataObjectFromColumn(data)
        )(j, _ != i)
      }
    )(startOption, _ != c.data)
  }

  private def uncoverColumn(c: ColumnObject): Unit = {
    def addColumnToHeaderList(): Unit = {
      c.data.R.L = c.data
      c.data.L.R = c.data
    }

    def addDataObjectBackIntoColumnInItsOriginalPosition(dataObject: DataObject): Unit = {
      dataObject.U.D = dataObject
      dataObject.D.U = dataObject
      dataObject.C.S += 1
    }

    val startOption = c.D

    doWhile(
      getNext = _.U,
      body = i => {
        val j = i.L
        doWhile(
          getNext = data => data.L,
          body = data => addDataObjectBackIntoColumnInItsOriginalPosition(data)
        )(j, _ != i)
      }
    )(startOption, _ != c.data)

    addColumnToHeaderList()
  }

  def makeHeadFromMatrix(inputMatrix: Array[Array[Int]]): ColumnObject = {
    val head: ColumnObject = ColumnObject.makeHead()

    def createColumnAndAppendHorizontally(columnIndex: Int): Unit = {
      val newColumn: ColumnObject = ColumnObject.makeColumn(
        isPrimary = true,
        N = Option(columnIndex)
      )
      appendHorizontally(newColumn.data, head.data)
    }

    def horizontallyLinkDataObjectsInSameRow(rowHead: DataObject, currentRowOfDataObjects: ListBuffer[DataObject]): Unit = {
      currentRowOfDataObjects.slice(1, currentRowOfDataObjects.length).foreach(
        eachDataObject => appendHorizontally(eachDataObject, rowHead)
      )
    }

    def verticallyLinkDataObjectsInGivenRowToTheirColumnsAndReturnAddedDataObjects(rowIndex: Int): ListBuffer[DataObject] = {
      var currentColumnHead = head.R
      var currentRowOfDataObjects = ListBuffer[DataObject]()

      val columnIndices = inputMatrix(rowIndex).indices
      columnIndices.foreach(
        columnIndex => {
          if (inputMatrix(rowIndex)(columnIndex) != 0) {
            val newDataObject = DataObject.newLinkedListNode(Option(rowIndex), currentColumnHead.C)

            appendVertically(newDataObject, currentColumnHead)
            currentRowOfDataObjects :+= newDataObject
          }
          currentColumnHead = currentColumnHead.R
        }
      )

      currentRowOfDataObjects
    }

    def appendHorizontally(that: DataObject, head: DataObject): Unit = {
      that.L = head.L
      that.R = head

      head.L.R = that
      head.L = that
    }

    def appendVertically(that: DataObject, head: DataObject): Unit = {
      that.U = head.U
      that.D = head

      head.U.D = that
      head.U = that
      head.C.S += 1
    }

    val columnIndices = inputMatrix(0).indices
    columnIndices.foreach(
      columnIndex => createColumnAndAppendHorizontally(columnIndex)
    )

    val rowIndices = inputMatrix.indices
    rowIndices.foreach(
      rowIndex => {
        val addedDataObjectsInGivenRow = verticallyLinkDataObjectsInGivenRowToTheirColumnsAndReturnAddedDataObjects(rowIndex)
        horizontallyLinkDataObjectsInSameRow(addedDataObjectsInGivenRow.head, addedDataObjectsInGivenRow)
      }
    )

    head
  }
}