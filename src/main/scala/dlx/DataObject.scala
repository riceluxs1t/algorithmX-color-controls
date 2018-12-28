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
  val optionId: Int
) {
//  def withL(newL: DataObject): DataObject = DataObject(newL, R, U, D, C, optionId)
//  def withR(newR: DataObject): DataObject = DataObject(L, newR, U, D, C, optionId)
//  def withU(newU: DataObject): DataObject = DataObject(L, R, newU, D, C, optionId)
//  def withD(newD: DataObject): DataObject = DataObject(L, R, U, newD, C, optionId)
//  def withC(newC: ColumnObject): DataObject = DataObject(L, R, U, D, newC, optionId)
}

object DataObject {
  def newLinkedListNode(optionId: Int): DataObject = {
    val newHead = new DataObject(null, null, null, null, null, optionId)
    newHead.L = newHead
    newHead.R = newHead
    newHead.U = newHead
    newHead.D = newHead
    newHead
  }
}
