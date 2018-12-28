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
  N: Int,
  var S: Int,
  var data: DataObject
)

object ColumnObject {
  def makeHead(): ColumnObject = {
    ColumnObject(false, 0, -1, null)
  }

  def makeNewPrimaryColumn(N: Int): ColumnObject = {
    val columnNewPrimary = ColumnObject(true, N, 0, null)
    val newData = new DataObject(null, null, null, null, null, -1)
    newData.L = newData
    newData.R = newData
    newData.U = newData
    newData.D = newData
    newData.C = columnNewPrimary
    columnNewPrimary.data = newData
    columnNewPrimary
  }
}
