package main.scala

/**
  * a case class that represents an item.
  * @param isPrimary true if a primary column that must be covered. false otherwise.
  * @param numOnes the number of ones if isPrimary is true. null otherwise.
  * @param identifier the identifier of an item.
  * @param L link to the left entry.
  * @param R link to the right entry.
  * @param U link to the above entry.
  * @param D link to the below entry.
  * @param toItemHeader link to the item.
  */
case class Item(
  isPrimary: Boolean,
  var numOnes: Int,
  var identifier: Int,
  override var L: Entry,
  override var R: Entry,
  override var U: Entry,
  override var D: Entry,
  override var toItemHeader: Entry
) extends Entry(L, R, U, D, toItemHeader)
