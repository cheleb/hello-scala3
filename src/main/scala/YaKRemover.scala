@main
def yak =
  println(s"Hello, YaKRemover! ${removeYaks("yykkyokak")}")

def removeYaks(s: String): String =
  s.toList match
    case 'y' :: _ :: 'k' :: tail => removeYaks(tail.mkString)
    case head :: tail            => head + removeYaks(tail.mkString)
    case Nil                     => ""
