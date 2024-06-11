package hellotypeclass

import show.*

import password.Password

final case class Person(
    name: String,
    age: Int,
    login: String,
    password: Password
)

object Person {
  given Show[Person] with {
    def show(p: Person): String =
      s"Person(name = ${p.name}, age = ${p.age}) ${p.login} ${p.password.show}"
  }
}
