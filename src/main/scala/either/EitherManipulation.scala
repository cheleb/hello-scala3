package either

/** Either: a value that is either a Left (error) or a Right (success). Right-biased. */
enum ParseError:
  case NotANumber(input: String)
  case Negative(n: Int)

def parseInt(s: String): Either[ParseError, Int] =
  s.toIntOption.toRight(ParseError.NotANumber(s))

def nonNegative(n: Int): Either[ParseError, Int] =
  Either.cond(n >= 0, n, ParseError.Negative(n))

/** Chain with for-comprehension: short-circuits on the first Left. */
def parseNonNegative(s: String): Either[ParseError, Int] =
  for
    n <- parseInt(s)
    p <- nonNegative(n)
  yield p

/** Parse each string in order, stopping at the first Left; later elements are never parsed. */
def parseAll(xs: List[String]): Either[ParseError, List[Int]] =
  @scala.annotation.tailrec
  def loop(rest: List[String], acc: List[Int]): Either[ParseError, List[Int]] =
    rest match
      case Nil => Right(acc.reverse)
      case s :: tail =>
        parseNonNegative(s) match
          case Right(n)   => loop(tail, n :: acc)
          case Left(err)  => Left(err)
  loop(xs, Nil)

@main def eitherDemo(): Unit =
  println(parseNonNegative("42"))
  println(parseNonNegative("abc"))
  println(parseNonNegative("-3"))

  // map / flatMap / left.map
  println(parseInt("10").map(_ * 2))
  println(parseInt("x").left.map(e => s"error: $e"))
  println(parseInt("10").flatMap(nonNegative))

  // fold: handle both sides
  val msg = parseNonNegative("7").fold(e => s"failed: $e", n => s"got $n")
  println(msg)

  // getOrElse / orElse
  println(parseInt("x").getOrElse(0))
  println(parseInt("x").orElse(parseInt("5")))

  // pattern matching
  parseNonNegative("-1") match
    case Right(n)                        => println(s"ok $n")
    case Left(ParseError.Negative(n))    => println(s"negative: $n")
    case Left(ParseError.NotANumber(in)) => println(s"not a number: $in")

  // partition a list of Eithers
  val (errors, oks) = List("1", "a", "3", "-2").map(parseNonNegative).partitionMap(identity)
  println(s"errors=$errors oks=$oks")

  // traverse-like
  println(parseAll(List("1", "2", "3")))
  println(parseAll(List("1", "oops", "-3")))

  // Option <-> Either, and swap
  println(Option(1).toRight("missing"))
  println(Right(1).swap)
