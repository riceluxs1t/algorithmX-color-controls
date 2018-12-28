package dlx

import org.scalatest._

class DLXChooseColumnWithMinimumSTest extends FunSpec with Matchers {
  describe("ColumnWithMinimumS") {
    describe("when only one column exists") {
      it("should be that column") {
        val matrix = Array(
          Array(1),
          Array(1)
        )

        val head = DLX.makeHeadFromMatrix(matrix)

        DLX.chooseColumnWithMinimumS(head).S should be (2)
        DLX.chooseColumnWithMinimumS(head).N should be (0)
      }
    }

    describe("when two columns exist with column2 smaller") {
      it("should be column0") {
        val matrix = Array(
          Array(1, 1),
          Array(0, 1)
        )

        val head = DLX.makeHeadFromMatrix(matrix)

        DLX.chooseColumnWithMinimumS(head).S should be (1)
        DLX.chooseColumnWithMinimumS(head).N should be (0)
      }
    }

    describe("when two columns exist with column1 smaller") {
      it("should be column1") {
        val matrix = Array(
          Array(1, 1),
          Array(1, 0)
        )

        val head = DLX.makeHeadFromMatrix(matrix)

        DLX.chooseColumnWithMinimumS(head).S should be (1)
        DLX.chooseColumnWithMinimumS(head).N should be (1)
      }
    }
  }
}
