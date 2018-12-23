package dlx

import org.scalatest._

class DLXTest extends FunSpec with Matchers {
  describe("ColumnWithMinimumS") {
    describe("when only one column exists") {
      it("should be that column") {
        val column = new ColumnObject(true, 1, 5, null, null, null, null, null)
        column.R = column

        DLX.chooseColumnWithMinimumS(column).S should be (5)
        DLX.chooseColumnWithMinimumS(column).N should be (1)
      }
    }
    describe("when two columns exist with column2 smaller") {
      it("should be column2") {
        val column1: ColumnObject = new ColumnObject(true, 1, 10, null, null, null, null, null)
        val column2: ColumnObject = new ColumnObject(true, 2, 5, null, null, null, null, null)
        column1.R = column2
        column2.R = column1

        DLX.chooseColumnWithMinimumS(column1).S should be (5)
        DLX.chooseColumnWithMinimumS(column1).N should be (2)
      }
    }

    describe("when two columns exist with column1 smaller") {
      it("should be column1") {
        val column1: ColumnObject = new ColumnObject(true, 1, 3, null, null, null, null, null)
        val column2: ColumnObject = new ColumnObject(true, 2, 5, null, null, null, null, null)
        column1.R = column2
        column2.R = column1

        DLX.chooseColumnWithMinimumS(column1).S should be (3)
        DLX.chooseColumnWithMinimumS(column1).N should be (1)
      }
    }

    describe("when three columns exist with column 2 smallest and column3 non primary") {
      it("should be column2") {
        val column1: ColumnObject = new ColumnObject(true, 1, 3, null, null, null, null, null)
        val column2: ColumnObject = new ColumnObject(true, 2, 2, null, null, null, null, null)
        val column3: ColumnObject = new ColumnObject(false, 3, 1, null, null, null, null, null)
        column1.R = column2
        column2.R = column3
        column3.R = column1

        DLX.chooseColumnWithMinimumS(column1).S should be (2)
        DLX.chooseColumnWithMinimumS(column1).N should be (2)
      }
    }
  }
}
