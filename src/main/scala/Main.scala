import zio.json.*
import java.io.OutputStream
import java.io.FileOutputStream

@main def hello: Unit =
  println("Hello, Scala 3!")

  // Using the resource management function
  withFile("target/example.txt") { out =>
    out.write("Hello, file!\n".getBytes)
  }

  val nasty = withFile("target/log.txt")(out => (i: Int) => out.write(i))
  nasty(1) // IOException

def withFile[T](name: String)(f: OutputStream => T): T =
  val out = new FileOutputStream(name) // Acquisition
  val result = f(out)

  out.close // Release
  result

//given JsonEncoder[Person] = JsonEncoder.string.contramap(_.name)

@main def json: Unit =
  // given JsonEncoder[Person] = JsonEncoder.int.contramap(_.age)
  msg

def msg(using JsonEncoder[Person]) =
  val person = Person(
    "John",
    Person.Password("123456"),
    30,
    List(Dog("Rex", 5), Cat("Felix", 3))
  )
  println(person.toJsonPretty)

@jsonDiscriminator("@type")
sealed trait Pet derives JsonCodec
case class Dog(name: String, age: Int) extends Pet
case class Cat(name: String, age: Int) extends Pet

case class Person(
    name: String,
    password: Person.Password,
    age: Int,
    pet: List[Pet]
) derives JsonCodec

object Person:
  trait Obscure[A <: String](f: A => String):
    given JsonEncoder[A] = JsonEncoder.string.contramap(f)

  opaque type Password <: String = String

  object Password extends Obscure(_ => "****"):
    def apply(value: String): Password = value
    def unapply(value: Password): Option[String] = Some(value)

    given JsonEncoder[Password] = JsonEncoder.string.contramap(_ => "!!!!")
    given JsonDecoder[Password] = JsonDecoder.string.map(Password(_))
