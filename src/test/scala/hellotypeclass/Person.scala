package hellotypeclass

import show.*

import Password.Password

final case class Person(
    name: String,
    age: Int,
    login: String,
    password: String
)

object Person {
  given Show[Person] with {
    def show(p: Person): String = s"Person(name = ${p.name}, age = ${p.age})"
  }
}
