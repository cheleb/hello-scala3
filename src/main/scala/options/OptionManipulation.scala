package options

/** Common ways to create, transform and consume an Option. */
def parseInt(s: String): Option[Int] = s.toIntOption

def half(n: Int): Option[Int] = if n % 2 == 0 then Some(n / 2) else None

extension [A](xs: List[Option[A]])
  /** Turns a List[Option[A]] into Some(list) only if every element is defined. */
  def sequence: Option[List[A]] =
    xs.foldRight(Option(List.empty[A]))((o, acc) => for x <- o; l <- acc yield x :: l)

@main def optionManipulationDemo(): Unit =
  // Creation
  val some: Option[Int] = Some(42)
  val none: Option[Int] = None
  val fromNullable: Option[String] = Option(null: String) // None

  // map / flatMap / filter
  println(some.map(_ * 2)) // Some(84)
  println(parseInt("8").flatMap(half)) // Some(4)
  println(parseInt("x").flatMap(half)) // None
  println(some.filter(_ > 100)) // None

  // Extracting values safely
  println(none.getOrElse(0))
  println(none.orElse(Some(7)))
  println(some.fold("empty")(n => s"got $n"))

  // Pattern matching
  fromNullable match
    case Some(s) => println(s"value $s")
    case None    => println("no value")

  // For-comprehension: short-circuits on the first None
  val sum = for
    a <- parseInt("1")
    b <- parseInt("2")
  yield a + b
  println(sum) // Some(3)

  // Collections
  val inputs = List("1", "two", "3")
  println(inputs.flatMap(parseInt)) // List(1, 3)
  println(inputs.map(parseInt).sequence)
  println(some.toList ++ none.toList)
  println(some.exists(_ > 10) && some.forall(_ > 10))
