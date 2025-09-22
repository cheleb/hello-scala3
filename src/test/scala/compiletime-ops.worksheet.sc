import scala.compiletime.constValue
import scala.compiletime.ops.int.S
import scala.compiletime.ops.int.+

transparent inline def toInt[N]: Int =
  inline constValue[N] match
    case 0       => 0
    case _: S[n] => 1 + toInt[n]

toInt[10]

val `10`: 5 + 5 = 10

import scala.compiletime.ops.*

type +[A <: Int | String, B <: Int | String] <: Int | String = (A, B) match
  case (Int, Int)       => int.+[A, B]
  case (String, String) => string.+[A, B]

val `Hello, World!`: "Hello, " + "World!" = "Hello, World!"

val m = 48

(1 to 10)
  .map(i => 48 * Math.pow(1.08, i))
  .zipWithIndex
  .foreach((m, a) => println(s"$a: $m")) // 49, 50, ..., 58
