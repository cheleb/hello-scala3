package tryoption

import scala.util.{Failure, Success, Try}

/** Converting between Option and Try, in both directions. */

// Option -> Try: supply the exception for the None case.
def optionToTry[A](o: Option[A], err: => Throwable): Try[A] =
  o.fold[Try[A]](Failure(err))(Success(_))

// Try -> Option: the exception is dropped.
def tryToOption[A](t: Try[A]): Option[A] =
  t.toOption

// Try -> Option of the failure.
def tryFailureToOption[A](t: Try[A]): Option[Throwable] =
  t.failed.toOption

/** Typed failure reasons, so callers can match instead of parsing text. */
enum ParseError extends Exception:
  case NotANumber(input: String)

def render(e: Throwable): String = e match
  case ParseError.NotANumber(input) => s"not a number: $input"
  case other                        => other.toString

/** Example: parse an Int, keeping the reason on failure. */
def parseInt(s: String): Try[Int] =
  optionToTry(s.toIntOption, ParseError.NotANumber(s))

@main def tryOptionDemo(): Unit =
  println(parseInt("42"))                               // Success(42)
  println(parseInt("x").failed.map(render))             // Success(not a number: x)
  println(tryToOption(parseInt("42")))                  // Some(42)
  println(tryToOption(parseInt("x")))                   // None
  println(tryFailureToOption(parseInt("x")).map(render)) // Some(not a number: x)
