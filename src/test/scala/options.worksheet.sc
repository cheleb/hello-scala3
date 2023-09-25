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
