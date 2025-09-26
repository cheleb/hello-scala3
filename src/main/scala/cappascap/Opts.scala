package cappascap

import scala.caps.*

class Console() extends SharedCapability:
  def println(s: String): Unit = System.out.println(s"console: $s")

def log(s: String)(using Console): Unit =
  summon[Console].println(s)

@main def capability: Unit =
  val addPure: (Int, Int) -> Int = (a, b) => a + b // same as before

  def writesToConsole(a: Int, b: Int)(using Console): Int =
    log(s"adding a ($a) to b ($b)")
    a + b

  writesToConsole(1, 2)(using Console()) // explicit capability passing
  // this will not compile because the function is no longer pure
  // it now captures the Console capability
  //  val addWritesToConsole: Console ?-> (Int, Int) -> Int = (a, b) =>
  //    log(s"adding a ($a) to b ($b)")
  //  a + b
