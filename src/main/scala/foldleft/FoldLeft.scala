package foldleft

/** foldLeft: process a collection left to right, threading an accumulator. */
def sum(xs: List[Int]): Int = xs.foldLeft(0)(_ + _)

/** Counts words per length; the accumulator type (a Map) differs from the element type. */
def lengthHistogram(words: List[String]): Map[Int, Int] =
  words.foldLeft(Map.empty[Int, Int]): (acc, w) =>
    val len = w.length
    acc.updated(len, acc.getOrElse(len, 0) + 1)

/** reverse is a foldLeft that prepends each element. */
def reverse[A](xs: List[A]): List[A] = xs.foldLeft(List.empty[A])((acc, x) => x :: acc)

@main def foldLeftDemo(): Unit =
  println(sum(List(1, 2, 3, 4)))
  println(lengthHistogram(List("a", "bb", "cc", "ddd")))
  println(reverse(List(1, 2, 3)))
  // Nesting shows left-to-right order: prints (((a)b)c)
  println(List("a", "b", "c").foldLeft("")((acc, s) => s"($acc$s)"))
