class Animal
class Dog extends Animal

val _: List[Animal] = List[Dog]()
//val _: Array[Animal] = Array[Dog]()

def test[A, F[_], G[_]](list: F[A], g: G[A])(using
    ev: F[A] <:< G[A]
): Unit = ()

test(List[Dog](new Dog, new Dog), List[Animal](new Dog, new Dog))
