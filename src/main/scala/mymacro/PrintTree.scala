package mymacro

import scala.annotation.MacroAnnotation
import scala.quoted.Quotes
import scala.annotation.experimental

@experimental
class printTree extends MacroAnnotation {
  override def transform(using
      q: Quotes
  )(
      tree: q.reflect.Definition,
      companion: Option[q.reflect.Definition]
  ): List[q.reflect.Definition] =
    import q.reflect.{*, given}

    println("print tree: " + tree.show(using Printer.TreeStructure))
    List(tree)
}
