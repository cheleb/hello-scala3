package cappascap

import scala.collection.immutable.Nil as ListNil
import scala.caps.cap

enum MyList[+A]:
    case Nil
    case Cons(head: A, tail: MyList[A])

    def map[B](f: A ->{} B): MyList[B] = this match
        case Nil => Nil
        case Cons(head, tail) => Cons(f(head), tail.map(f))

object MyList:
    def apply[A](xs: A*): MyList[A] = xs.toList match
        case ListNil => MyList.Nil
        case head :: xs  => MyList.Cons(head, apply(xs*))
        
    

@main
def test =
    var a: Integer^ = 1
    val xs = MyList(Integer.valueOf(1))
    xs.map:
         i =>
            //a + 1 // will not compile
            i+1
    println(xs)
    println(s"var $a")    


