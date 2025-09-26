@main
def yak =
  println(s"Hello, YaKRemover! ${removeYaks("yykkyokak".toList)}")
import scala.annotation.tailrec

@tailrec
def removeYaks(s: List[Char], acc: String = ""): String =
  s match
    case 'y' :: _ :: 'k' :: tail => removeYaks(tail, acc)
    case head :: tail            => removeYaks(tail, acc + head)
    case Nil                     => acc
