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
