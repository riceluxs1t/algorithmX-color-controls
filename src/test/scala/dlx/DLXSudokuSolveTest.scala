package dlx

import org.scalatest.{FunSpec, Matchers}

class DLXSudokuSolveTest extends FunSpec with Matchers {
  describe("Sudoku Solver") {
    val puzzle = Array(
      Array(6, 7, 0, 1, 0, 0, 0, 9, 0),
      Array(8, 0, 0, 3, 0, 9, 0, 0, 0),
      Array(0, 0, 4, 2, 0, 8, 0, 3, 0),
      Array(0, 0, 0, 0, 0, 0, 6, 8, 2),
      Array(9, 6, 0, 8, 0, 3, 0, 4, 7),
      Array(7, 4, 8, 0, 0, 0, 0, 0, 0),
      Array(0, 8, 0, 4, 0, 1, 9, 0, 0),
      Array(0, 0, 0, 9, 0, 2, 0, 0, 6),
      Array(0, 3, 0, 0, 0, 7, 0, 2, 1)
    )

    it("should be the solved version") {
      DLXSudoku.solveSudoku(puzzle) should be
        Array(
          Array(6, 7, 3, 1, 4, 5, 2, 9, 8),
          Array(8, 2, 5, 3, 7, 9, 1, 6, 4),
          Array(1, 9, 4, 2, 6, 8, 7, 3, 5),
          Array(3, 5, 1, 7, 9, 4, 6, 8, 2),
          Array(9, 6, 2, 8, 1, 3, 5, 4, 7),
          Array(7, 4, 8, 5, 2, 6, 3, 1, 9),
          Array(2, 8, 6, 4, 5, 1, 9, 7, 3),
          Array(4, 1, 7, 9, 3, 2, 8, 5, 6),
          Array(5, 3, 9, 6, 8, 7, 4, 2, 1)
        )
    }
  }
}
