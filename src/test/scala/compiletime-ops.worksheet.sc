import scala.compiletime.constValue
import scala.compiletime.ops.int.S

transparent inline def toInt[N]: Int =
  inline constValue[N] match
    case 0       => 0
    case _: S[n] => 1 + toInt[n]

toInt[10]
