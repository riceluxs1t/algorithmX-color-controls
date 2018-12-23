package dlx

class DLX(head: ColumnObject) {

}

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
}