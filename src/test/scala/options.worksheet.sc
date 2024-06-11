trait XFunctor[F[_]] {
  def xmap[A, B](fa: F[A])(f: A => B, g: B => A): F[B]
}

object XFunctor {
  implicit val optionXFunctor: XFunctor[Option] = new XFunctor[Option] {
    override def xmap[A, B](fa: Option[A])(f: A => B, g: B => A): Option[B] =
      fa.map(f)
  }
}

trait Contravariant[F[_]] extends XFunctor[F] {
  def contramap[A, B](fa: F[A])(f: B => A): F[B]
}

def curry[A, B, C](f: (A, B) => C): A => B => C = a => (b => f(a, b))

val f3 = (x: Int, y: Int) => x + y

val f4 = curry(f3)

f3(1, 2)
val f5 = f4(1)
f5(10)
