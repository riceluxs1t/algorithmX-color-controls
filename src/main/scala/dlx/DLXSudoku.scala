package dlx

import scala.collection.mutable

object DLXSudoku {
  def solveSudoku(board: Array[Array[Int]]): Array[Array[Int]] = {
    val R = board.length
    val C = board.head.length
    val N = R * C

    val allPositions = for (
      r <- board.indices;
      c <- board.head.indices
    ) yield (r, c)

    var optionId = 0
    var optionIdToPositionWithValue = mutable.Map[Int, (Int, Int, Int)]()
    for ((r, c) <- allPositions) {
      if (board(r)(c) != 0) {
        optionIdToPositionWithValue += (optionId -> (r, c, board(r)(c)))
        optionId += 1
      } else {
        for (v <- 1 to 9) {
          optionIdToPositionWithValue += (optionId -> (r, c, v))
          optionId += 1
        }
      }
    }

    val exactCoverMatrix = allPositions.foldLeft(Array[Array[Int]]())(
      (exactCoverMatrix, position) => exactCoverMatrix ++ generateOptionsBasedAtPosition(position, board)
    )

    val head = DLX.makeHeadFromMatrix(exactCoverMatrix)
    val ans = DLX.solve(head)

    for (optionId <- ans.head) {
      val (r, c, v) = optionIdToPositionWithValue(optionId)
      board(r)(c) = v
    }

    board
  }

  private def generateOptionsBasedAtPosition(position: (Int, Int), board: Array[Array[Int]]): Array[Array[Int]] = {
    val (r, c) = position
    val entryValue = board(r)(c)

    if (entryValue != 0) {
      Array(generateOptionBasedOnMarkingPositionWithValue(r, c, entryValue - 1))
    } else {
      (0 to 8).map(v => generateOptionBasedOnMarkingPositionWithValue(r, c, v)).toArray
    }
  }

  private def generateOptionBasedOnMarkingPositionWithValue(r: Int, c: Int, v: Int): Array[Int] = {
    val option = Array.ofDim[Int](81 * 4)

    // cell (r, c) has been filled.
    option(r * 9 + c) = 1
    // col c has number v in it
    option(81 + r * 9 + v) = 1
    // row r has number v in it
    option(81 * 2 + c * 9 + v) = 1
    // grid b has number v in it
    option(81 * 3 + getGrid(r, c) * 9 + v) = 1
    option
  }

  private def getGrid(r: Int, c: Int): Int = {
    3 * (r / 3) + (c / 3)
  }
}
