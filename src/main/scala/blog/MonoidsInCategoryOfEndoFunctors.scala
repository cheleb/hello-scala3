package blog

object MonoidsInCategoryOfEndoFunctors {

  trait MostAbstractMonoid[T, ~>[_, _], U, P] {
    def unit: U ~> T // same as ~>[U, T]
    def combine: P ~> T
  }

  trait GeneralMonoid[T, U, P] extends MostAbstractMonoid[T, Function1, U, P] {
    // def unit: U => T
    // def combine: P => T
  }

  trait FunctionalMonoid[T] extends GeneralMonoid[T, Unit, (T, T)] {
//    def unit: Unit => T
//    def combine: ((T, T)) => T
  }

  trait Monoid[T] extends FunctionalMonoid[T] {
    def empty: T
    def combine(a: T, b: T): T

    //
    def unit: Unit => T = _ => empty

    def combine: ((T, T)) => T = t => combine(t._1, t._2)
  }

  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }
}
