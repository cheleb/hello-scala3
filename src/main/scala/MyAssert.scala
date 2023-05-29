import scala.quoted.*

inline def assert(inline expr: Boolean): Unit =
  ${ assertImpl('expr) }

def assertImpl(expr: Expr[Boolean])(using Quotes): Expr[Unit] = '{
  if (! $expr)
    throw new AssertionError(s"failed assertion")
}
