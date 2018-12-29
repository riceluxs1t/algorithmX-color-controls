package dlx

/**
  * a case class that represents an item.
  *
  * @param isPrimary true if a primary column that must be covered. false otherwise.
  * @param N the identifier of an item.
  * @param S the number of ones if isPrimary is true. -1 otherwise.
  */
case class ColumnObject(
  isPrimary: Boolean,
  N: Option[Int],
  var S: Int,
  var data: DataObject
) {
  def L: DataObject = data.L
  def R: DataObject = data.R
  def U: DataObject = data.U
  def D: DataObject = data.D
}

object ColumnObject {
  def makeHead(): ColumnObject = {
    makeColumn(isPrimary = false, Option.empty)
  }

  def makeColumn(isPrimary: Boolean, N: Option[Int]): ColumnObject = {
    val columnNew = ColumnObject(isPrimary, N, 0, null)
    val newData = DataObject.newLinkedListNode(Option.empty, columnNew)
    columnNew.data = newData

    columnNew
  }


}
