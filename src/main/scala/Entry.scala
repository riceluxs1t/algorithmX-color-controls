package main.scala

/**
  * A case class that represents an entry in the exact cover matrix.
  * Doubly linked in an item and an option the entry belongs to.
  * @param L link to the left entry.
  * @param R link to the right entry.
  * @param U link to the above entry.
  * @param D link to the below entry.
  * @param toItemHeader link to the item.
  */
case class Entry(var L: Entry, var R: Entry, var U: Entry, var D: Entry, var toItemHeader: Entry)
