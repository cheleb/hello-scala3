package hellotypeclass.show

extension [A](a: A)(using s: Show[A]) def show: String = s.show(a)
