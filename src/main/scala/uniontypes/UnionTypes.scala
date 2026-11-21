package uniontypes

/** Union types: a value that is one of several unrelated types, with no wrapper. */
type StringOrInt = String | Int

def describe(x: StringOrInt): String = x match
  case s: String => s"string of length ${s.length}"
  case i: Int    => s"int $i"

/** Unions can model a closed set of result types without a wrapper. */
case class User(name: String)
case class NotFound(id: Int)
case class Forbidden(user: String)

def lookup(id: Int): User | NotFound | Forbidden =
  id match
    case 1 => User("alice")
    case 2 => Forbidden("bob")
    case _ => NotFound(id)

def render(r: User | NotFound | Forbidden): String = r match
  case User(name)      => s"found $name"
  case NotFound(id)    => s"no such id $id"
  case Forbidden(user) => s"$user is forbidden"

/** The element type of a mixed List is inferred as a union. */
val widened: List[Int | String | Double] = List(1, "two", 3.0)

/** Nullable via union with Null (useful with explicit nulls). */
def orEmpty(s: String | Null): String = if s == null then "" else s

@main def unionTypesDemo(): Unit =
  println(describe("hello"))
  println(describe(42))
  (1 to 3).map(lookup).map(render).foreach(println)
  // Union types are commutative: A | B =:= B | A
  val swapped: Int | String = ("x": String | Int)
  println(swapped)
  println(widened)
  println(orEmpty(null))
