import scala.annotation.experimental
import mymacro.printTree
@experimental
object TestPrintTreeMacro extends App {

  @printTree
  def hello = "shello"

  hello
}
