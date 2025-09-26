package hellotypeclass.show

import scala.collection.AbstractIterable
import scala.compiletime.{erasedValue, error, summonInline}
import scala.deriving.*

// TODO implicits / using

trait Show[T] {
  def show(t: T): String
}

inline def summonInstances[T, Elems <: Tuple]: List[Show[?]] =
  inline erasedValue[Elems] match
    case _: (elem *: elems) =>
      deriveOrSummon[T, elem] :: summonInstances[T, elems]
    case _: EmptyTuple => Nil

inline def deriveOrSummon[T, Elem]: Show[Elem] =
  inline erasedValue[Elem] match
    case _: T => deriveRec[T, Elem]
    case _    => summonInline[Show[Elem]]

inline def deriveRec[T, Elem]: Show[Elem] =
  inline erasedValue[T] match
    case _: Elem => error("infinite recursive derivation")
    case _       =>
      Show.derived[Elem](using
        summonInline[Mirror.Of[Elem]]
      ) // recursive derivation

object Show:
  given Show[Int] with
    def show(i: Int): String = i.toString

  given Show[String] with {
    def show(s: String): String = s
  }

  inline def derived[T](using m: Mirror.Of[T]): Show[T] =
    lazy val elemInstances = summonInstances[T, m.MirroredElemTypes] // (1)
    inline m match // (2)
      case s: Mirror.SumOf[T]     => showSum(s, elemInstances)
      case p: Mirror.ProductOf[T] => showProduct(elemInstances)

  def showProduct[T](shows: -> List[Show[?]]): Show[T] = // (1)
    new Show[T]: // (2)
      def show(t: T): String =
        (t.asInstanceOf[Product]
          .productIterator)
          .zip(shows.iterator)
          .map { // (3)
            case (p, s) => s.asInstanceOf[Show[Any]].show(p) // (4)
          }
          .mkString("{", ", ", "}") // (5

  def showSum[T](s: Mirror.SumOf[T], elems: -> List[Show[?]]): Show[T] =
    new Show[T]:
      def show(t: T): String =
        val index = s.ordinal(t) // (2)
        elems(index).asInstanceOf[Show[Any]].show(t)
