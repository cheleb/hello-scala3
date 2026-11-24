package intersectiontypes

import java.util.concurrent.atomic.AtomicReference

/** Intersection types: a value that is several types at once (`A & B`). */
trait Named:
  def name: String

trait Aged:
  def age: Int

case class Person(name: String, age: Int) extends Named, Aged

/** The parameter must be both Named and Aged. */
def introduce(x: Named & Aged): String = s"${x.name} is ${x.age} years old"

/** Intersection is commutative: A & B =:= B & A. */
def introduceSwapped(x: Aged & Named): String = introduce(x)

/** Members of both sides are available. */
trait Resettable:
  def reset(): Unit

trait Growable[T]:
  def add(t: T): Unit

class Counter extends Resettable, Growable[Int]:
  private val ref = AtomicReference(List.empty[Int])
  def items: List[Int] = ref.get()
  def add(t: Int): Unit = ref.updateAndGet(t :: _)
  def reset(): Unit = ref.set(Nil)

def fillThenReset(x: Resettable & Growable[Int]): Unit =
  x.add(1)
  x.add(2)
  x.reset()

/** An anonymous object can satisfy an intersection directly. */
val anon: Named & Aged = new Named with Aged:
  val name = "anon"
  val age = 7

@main def intersectionTypesDemo(): Unit =
  println(introduce(Person("alice", 30)))
  println(introduceSwapped(anon))
  val c = Counter()
  c.add(5)
  fillThenReset(c)
  println(c.items)
