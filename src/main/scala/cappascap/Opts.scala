package cappascap

import scala.language.*

object Opts {

  def allOk[T](xs: List[Option[T]]): Option[List[T]] =
    Option:
      xs.map(_.get)
}
