package blog

/** Sample of Scala 3 type lambdas: `[X] =>> F[X]`. */
object TypeLambdas:

  trait Functor[F[_]]:
    extension [A](fa: F[A]) def map[B](f: A => B): F[B]

  given Functor[List] with
    extension [A](fa: List[A]) def map[B](f: A => B): List[B] = fa.map(f)

  // Partial application of a two-parameter type: Either[String, _]
  type StringOr = [A] =>> Either[String, A]

  given Functor[StringOr] with
    extension [A](fa: Either[String, A])
      def map[B](f: A => B): Either[String, B] = fa.map(f)

  // Inline type lambda, no alias needed
  given Functor[[A] =>> Map[String, A]] with
    extension [A](fa: Map[String, A])
      def map[B](f: A => B): Map[String, B] = fa.view.mapValues(f).toMap

  // Fixing the first parameter of a tuple
  type Pair = [A] =>> (Int, A)

  // Type lambda as a type-level function: wrap in Option and List
  type OptList = [A] =>> Option[List[A]]

  def double[F[_]](fa: F[Int])(using Functor[F]): F[Int] = fa.map(_ * 2)

  @main def typeLambdasDemo(): Unit =
    println(double(List(1, 2, 3)))
    println(double[StringOr](Right(21)))
    println(double[[A] =>> Map[String, A]](Map("a" -> 1)))
    val p: Pair[String] = (1, "one")
    val o: OptList[Int] = Some(List(1, 2))
    println((p, o))
