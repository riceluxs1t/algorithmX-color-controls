package dlx

import org.scalatest.{FunSpec, Matchers}


class DLXSolveTest extends FunSpec with Matchers {
  describe("Solve") {
    describe("A simple matrix") {
      it("should be option 0") {
        val matrix = Array(
          Array(1, 1),
          Array(1, 0)
        )

        val head = DLX.makeHeadFromMatrix(matrix)

        val ans = DLX.solve(head).map(_.toSet).toSet

        ans should be (Set(Set(0)))
      }
    }

    describe("A wikipedia matrix") {
      it("should be options 1, 3, 5") {
        val matrix = Array(
          Array(1, 0, 0, 1, 0, 0, 1),
          Array(1, 0, 0, 1, 0, 0, 0),
          Array(0, 0, 0, 1, 1, 0, 1),
          Array(0, 0, 1, 0, 1, 1, 0),
          Array(0, 1, 1, 0, 0, 1, 1),
          Array(0, 1, 0, 0, 0, 0, 1)
        )

        val head = DLX.makeHeadFromMatrix(matrix)

        val ans = DLX.solve(head).map(_.toSet).toSet

        ans should be (Set(Set(1, 3, 5)))
      }
    }

    describe("A modified wikipedia matrix") {
      it("should be sets of options (1, 3, 5) and option (6)") {
        val matrix = Array(
          Array(1, 0, 0, 1, 0, 0, 1),
          Array(1, 0, 0, 1, 0, 0, 0),
          Array(0, 0, 0, 1, 1, 0, 1),
          Array(0, 0, 1, 0, 1, 1, 0),
          Array(0, 1, 1, 0, 0, 1, 1),
          Array(0, 1, 0, 0, 0, 0, 1),
          Array(1, 1, 1, 1, 1, 1, 1)
        )

        val head = DLX.makeHeadFromMatrix(matrix)

        val ans = DLX.solve(head).map(_.toSet).toSet

        ans should be (Set(Set(1, 3, 5), Set(6)))
      }
    }
  }
}
