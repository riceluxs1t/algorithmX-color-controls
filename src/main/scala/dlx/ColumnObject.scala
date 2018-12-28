package dlx

/**
  * a case class that represents an item.
 *
  * @param isPrimary true if a primary column that must be covered. false otherwise.
  * @param N the identifier of an item.
  * @param S the number of ones if isPrimary is true. null otherwise.
  * @param L link to the left entry.
  * @param R link to the right entry.
  * @param U link to the above entry.
  * @param D link to the below entry.
  * @param C link to the item.
  */
class ColumnObject(
  var isPrimary: Boolean,
  var N: Int,
  var S: Int,
  L: DataObject,
  R: DataObject,
  U: DataObject,
  D: DataObject,
  C: DataObject,
  optionId: Int
) extends DataObject(L, R, U, D, C, optionId)
