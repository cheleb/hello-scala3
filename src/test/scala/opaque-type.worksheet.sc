object Ages:

  opaque type Age = Int
  object Age:
    def apply(w: Int): Age = w
    extension (a: Age)
//      def value: Int = a
      def >=(b: Age): Boolean = a >= b

import Ages.*

val a = Age(20)
val b = Age(1)

//b >= a

isAdult(a)

def isAdult(age: Age): Boolean = age >= Age(18)
