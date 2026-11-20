package matchtypes

import scala.compiletime.constValue

/** Match types: type-level pattern matching, reduced by the compiler. */
type Elem[X] = X match
  case String      => Char
  case Iterable[t] => t

/** Recursive match type: flatten nested lists to their innermost element type. */
type Leaf[X] = X match
  case List[t] => Leaf[t]
  case _       => X

/** Total: returns None for empty input; the bound rules out unsupported types. */
def firstElem[X <: String | Iterable[?]](x: X): Option[Elem[X]] =
  val r: Option[Any] = (x: Any) match
    case s: String      => s.headOption
    case i: Iterable[?] => i.headOption
    case _              => None
  r.asInstanceOf[Option[Elem[X]]]

/** Type-level Peano-like computation with Int constants. */
type Len[T <: Tuple] <: Int = T match
  case EmptyTuple => 0
  case _ *: t     => scala.compiletime.ops.int.S[Len[t]]

@main def matchTypesDemo(): Unit =
  val c: Option[Char] = firstElem("hello")
  val i: Option[Int] = firstElem(List(1, 2, 3))
  println(s"$c $i")

  val leaf: Leaf[List[List[Int]]] = 42
  println(leaf)

  val n: Len[(Int, String, Boolean)] = 3
  println(constValue[Len[(Int, String, Boolean)]])
  println(n)
