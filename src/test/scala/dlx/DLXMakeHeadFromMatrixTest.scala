package dlx

import org.scalatest.{FunSpec, Matchers}

class DLXMakeHeadFromMatrixTest extends FunSpec with Matchers {
  describe("MakeHeadFromMatrix") {
    describe("when a non empty matrix is given") {
      it("should be a data object with columns with data objects linked correctly") {
        val matrix = Array(
          Array(1, 1),
          Array(1, 1)
        )

        val head = DLX.makeHeadFromMatrix(matrix)

        head.isPrimary should be (false)
        head.S should be (0)
        head.R should not be head.data
        head.R.R should not be head.data
        head.R.R.R should be (head.data)
        head.L should not be head.data
        head.L.R should be (head.data)

        head.R.S should be (2)
        head.R.D.D.D should be (head.R)
        head.R.U.U.U should be (head.R)
        head.R.D.R should be (head.R.R.D)
        head.R.D.D.R should be (head.R.R.D.D)
        head.R.D.L should be (head.R.R.D)
        head.R.D.D.L should be (head.R.R.D.D)

        head.R.R.S should be (2)
        head.R.R.D.D.D should be (head.R.R)
        head.R.R.U.U.U should be (head.R.R)
        head.R.R.D.R should be (head.R.D)
        head.R.R.D.D.R should be (head.R.D.D)
        head.R.R.D.L should be (head.R.D)
        head.R.R.D.D.L should be (head.R.D.D)
      }
    }
  }
}
