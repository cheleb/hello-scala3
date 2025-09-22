@main
def yak =
  println(s"Hello, YaKRemover! ${removeYaks("yykkyokak")}")
import scala.annotation.tailrec

@tailrec
def removeYaks(s: String, acc: String = ""): String =
  s.toList match
    case 'y' :: _ :: 'k' :: tail => removeYaks(tail.mkString, acc)
    case head :: tail            => removeYaks(tail.mkString, acc + head)
    case Nil                     => acc
