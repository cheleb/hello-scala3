package hellotypeclass

import show.*

object HelloTypeClass extends App {
  val p = Person("John", 42, "zozo", password("zozso"))

  println(p)
  def log[T](t: T)(using s: Show[T]): Unit = println(t.show)

  // def logOld[T: Show](t: T): Unit = println(summon[Show[T]].show(t))

  log(p)
  println(p.show)
}
