package hellotypeclass.show

// TODO implicits / using

trait Show[T] {
  def show(t: T): String
}

extension [T](t: T) {
  def show(using s: Show[T]): String = s.show(t)
}
