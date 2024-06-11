package mirrors

import scala.deriving.*
import scala.compiletime.{erasedValue, summonInline}

trait Eq[T]:
  def eqv(x: T, y: T): Boolean

object BasicDerivation {

  inline def derived[T](using m: Mirror.Of[T]): Eq[T] =
    val elemInstances = summonInstances[T, m.MirroredElemTypes]

    m match
      case s: Mirror.SumOf[T]     => sum(s, elemInstances)
      case p: Mirror.ProductOf[T] => product(p, elemInstances)

  inline def summonInstances[T, M <: Tuple]: List[Eq[_]] =
    inline erasedValue[M] match
      case _: EmptyTuple => Nil
      case _: (t *: ts)  => summonInline[Eq[t]] :: summonInstances[T, ts]

  inline def sum[T](s: Mirror.SumOf[T], elems: List[Eq[_]]): Eq[T] =
    ???
  inline def product[T](p: Mirror.ProductOf[T], elems: List[Eq[_]]): Eq[T] =
    compiletime.constValue[p.MirroredLabel]
    ???
}
