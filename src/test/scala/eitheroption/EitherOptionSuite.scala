package eitheroption

class EitherOptionSuite extends munit.FunSuite:

  test("optionToEither: Some -> Right") {
    assertEquals(optionToEither(Some(1), "err"), Right(1))
  }

  test("optionToEither: None -> Left(err)") {
    assertEquals(optionToEither(None: Option[Int], "err"), Left("err"))
  }

  test("optionToEither: err is not evaluated for Some") {
    var evaluated = false
    optionToEither(Some(1), { evaluated = true; "err" })
    assert(!evaluated)
  }

  test("eitherToOption: Right -> Some") {
    assertEquals(eitherToOption(Right(1): Either[String, Int]), Some(1))
  }

  test("eitherToOption: Left -> None") {
    assertEquals(eitherToOption(Left("e"): Either[String, Int]), None)
  }

  test("optionToLeft: Some -> Left, None -> Right") {
    assertEquals(optionToLeft(Some("e"), 1), Left("e"))
    assertEquals(optionToLeft(None: Option[String], 1), Right(1))
  }

  test("eitherLeftToOption: Left -> Some, Right -> None") {
    assertEquals(eitherLeftToOption(Left("e"): Either[String, Int]), Some("e"))
    assertEquals(eitherLeftToOption(Right(1): Either[String, Int]), None)
  }

  test("parseInt: valid number") {
    assertEquals(parseInt("42"), Right(42))
  }

  test("parseInt: invalid inputs") {
    assertEquals(parseInt("x"), Left(ParseError.NotANumber("x")))
    assertEquals(parseInt(""), Left(ParseError.NotANumber("")))
    assertEquals(
      parseInt("99999999999"),
      Left(ParseError.NotANumber("99999999999"))
    )
  }

  test("render NotANumber") {
    assertEquals(render(ParseError.NotANumber("x")), "not a number: x")
  }
