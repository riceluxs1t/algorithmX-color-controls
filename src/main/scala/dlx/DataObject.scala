package dlx

/**
  * A case class that represents an entry in the exact cover matrix.
  * Doubly linked in an item and an option the entry belongs to.
  * @param L link to the left.
  * @param R link to the right.
  * @param U link to the above.
  * @param D link to the below.
  * @param C link to the list header.
  */
class DataObject(var L: DataObject, var R: DataObject, var U: DataObject, var D: DataObject, var C: DataObject)
