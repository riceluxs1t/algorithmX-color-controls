package dlx

/**
  * A case class that represents an entry in the exact cover matrix.
  * Doubly linked in an item and an option the entry belongs to.
  * @param L link to the left.
  * @param R link to the right.
  * @param U link to the above.
  * @param D link to the below.
  * @param C link to the list header.
  * @param optionId the option id this data object belongs to.
  */
class DataObject(
  var L: DataObject,
  var R: DataObject,
  var U: DataObject,
  var D: DataObject,
  var C: ColumnObject,
  val optionId: Option[Int]
)
//    newColumn.data.L = head.L
//    newColumn.data.R = head.data
//    head.data.L.R = newColumn.data
//    head.data.L = newColumn.data

object DataObject {
  def newLinkedListNode(optionId: Option[Int], columnObject: ColumnObject): DataObject = {
    val newHead = new DataObject(null, null, null, null, null, optionId)
    newHead.L = newHead
    newHead.R = newHead
    newHead.U = newHead
    newHead.D = newHead
    newHead.C = columnObject
    newHead
  }
}
