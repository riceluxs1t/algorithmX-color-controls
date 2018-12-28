package dlx

import org.scalatest.{FunSpec, Matchers}

class DLXMakeHeadFromMatrixTest extends FunSpec with Matchers {
  describe("MakeHeadFromMatrix") {
    describe("when an empty matrix is given") {
      it("should be a data object with just columns") {
        val matrix = Array(
          Array(0, 0),
          Array(0, 0)
        )

        val head = DLX.makeHeadFromMatrix(matrix)

        head.isPrimary should be (false)
        head.S should be (-1)
        head.R should not be head
        head.R.R should not be head
        head.R.R.R should be (head)
        head.L should not be head
        head.L.R should be (head)

        head.R.asInstanceOf[ColumnObject].S should be (0)
        head.R.asInstanceOf[ColumnObject].D should be (head.R)
        head.R.asInstanceOf[ColumnObject].U should be (head.R)

        head.R.R.asInstanceOf[ColumnObject].S should be (0)
        head.R.R.asInstanceOf[ColumnObject].D should be (head.R.R)
        head.R.R.asInstanceOf[ColumnObject].U should be (head.R.R)
      }
    }
  }

  describe("MakeHeadFromMatrix") {
    describe("when a non empty matrix is given") {
      it("should be a data object with columns with data objects linked correctly") {
        val matrix = Array(
          Array(1, 1),
          Array(1, 1)
        )

        val head = DLX.makeHeadFromMatrix(matrix)

        head.isPrimary should be (false)
        head.S should be (-1)
        head.R should not be head
        head.R.R should not be head
        head.R.R.R should be (head)
        head.L should not be head
        head.L.R should be (head)

        head.R.asInstanceOf[ColumnObject].S should be (2)
        head.R.asInstanceOf[ColumnObject].D.D.D should be (head.R)
        head.R.asInstanceOf[ColumnObject].U.U.U should be (head.R)
        head.R.asInstanceOf[ColumnObject].D.R should be (head.R.R.D)
        head.R.asInstanceOf[ColumnObject].D.D.R should be (head.R.R.D.D)
        head.R.asInstanceOf[ColumnObject].D.L should be (head.R.R.D)
        head.R.asInstanceOf[ColumnObject].D.D.L should be (head.R.R.D.D)

        head.R.R.asInstanceOf[ColumnObject].S should be (2)
        head.R.R.asInstanceOf[ColumnObject].D.D.D should be (head.R.R)
        head.R.R.asInstanceOf[ColumnObject].U.U.U should be (head.R.R)
        head.R.R.asInstanceOf[ColumnObject].D.R should be (head.R.D)
        head.R.R.asInstanceOf[ColumnObject].D.D.R should be (head.R.D.D)
        head.R.R.asInstanceOf[ColumnObject].D.L should be (head.R.D)
        head.R.R.asInstanceOf[ColumnObject].D.D.L should be (head.R.D.D)
      }
    }
  }
}
