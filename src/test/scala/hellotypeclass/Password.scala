package hellotypeclass

import hellotypeclass.show.Show

object User:
  opaque type Password = String
  def password(str: String): Either[String, Password] =
    Either.cond(str.length >= 8, str, "Password too short")

  given Show[Password] with
    def show(p: Password): String = "******"

  opaque type Email = String

  def email(value: String): Either[String, User.Email] =
    if value.contains("@") then Right(value)
    else Left("Invalid email")

case class User(email: User.Email, password: User.Password)

@main
def main =
//  val user = User("", "")
  val user2 = for
    email <- User.email("@")
    password <- User.password("notsecured")
  yield User(email, password)
  user2 match
    case Left(error) => println(s"Error: $error")
    case Right(user) => println(s"User: $user")
