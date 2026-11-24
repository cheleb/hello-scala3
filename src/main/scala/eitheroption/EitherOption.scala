package eitheroption

/** Converting between Option and Either, in both directions. */

// Option -> Either: supply the "left" value used when the Option is empty.
def optionToEither(o: Option[Int], err: => String): Either[String, Int] =
  o.toRight(err)

def optionToLeft(o: Option[String], right: => Int): Either[String, Int] =
  o.toLeft(right)

// Either -> Option: the Left is discarded.
def eitherToOption(e: Either[String, Int]): Option[Int] = e.toOption

def eitherLeftToOption(e: Either[String, Int]): Option[String] = e.left.toOption

@main def eitherOptionDemo(): Unit =
  // Option -> Either
  println(optionToEither(Some(1), "missing")) // Right(1)
  println(optionToEither(None, "missing"))    // Left(missing)
  println(optionToLeft(Some("oops"), 0))      // Left(oops)
  println(optionToLeft(None, 0))              // Right(0)

  // Either -> Option
  println(eitherToOption(Right(1)))           // Some(1)
  println(eitherToOption(Left("boom")))       // None
  println(eitherLeftToOption(Left("boom")))   // Some(boom)

  // Round trip: Right survives, Left info is lost
  println(eitherToOption(optionToEither(Some(5), "x"))) // Some(5)
  println(eitherToOption(optionToEither(None, "x")))    // None

  // Either.cond / Option.when build each from a boolean
  println(Either.cond(3 > 2, 3, "too small")) // Right(3)
  println(Option.when(3 > 2)(3))              // Some(3)
