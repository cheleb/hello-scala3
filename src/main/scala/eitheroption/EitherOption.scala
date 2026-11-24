package eitheroption

/** Converting between Option and Either, in both directions. */

// Option -> Either: supply the error for the None case.
def optionToEither[E, A](o: Option[A], err: => E): Either[E, A] =
  o.toRight(err)

// Either -> Option: the error is dropped.
def eitherToOption[E, A](e: Either[E, A]): Option[A] =
  e.toOption

// Left-biased variants.
def optionToLeft[E, A](o: Option[E], right: => A): Either[E, A] =
  o.toLeft(right)

def eitherLeftToOption[E, A](e: Either[E, A]): Option[E] =
  e.left.toOption

/** Typed failure reasons, so callers can match instead of parsing text. */
enum ParseError:
  case NotANumber(input: String)

def render(e: ParseError): String = e match
  case ParseError.NotANumber(input) => s"not a number: $input"

/** Example: parse an Int, keeping the reason on failure. */
def parseInt(s: String): Either[ParseError, Int] =
  optionToEither(s.toIntOption, ParseError.NotANumber(s))

@main def eitherOptionDemo(): Unit =
  println(parseInt("42"))                // Right(42)
  println(parseInt("x").left.map(render)) // Left(not a number: x)
  println(eitherToOption(parseInt("42"))) // Some(42)
  println(eitherToOption(parseInt("x")))  // None
  println(eitherLeftToOption(parseInt("x")).map(render)) // Some(not a number: x)
