package files

// Basic file manipulation with java.nio and scala.util.Using.
// Failures are returned as values (Either) instead of thrown.

import java.io.IOException
import java.nio.file.{Files, NoSuchFileException, Path, StandardOpenOption}
import java.nio.charset.StandardCharsets
import scala.jdk.CollectionConverters.*
import scala.util.Using

enum FileFail:
  case NotFound(path: Path)
  case Io(error: IOException)

/** Runs a java.nio operation, converting IOException into a FileFail. */
def catching[T](path: Path)(op: => T): Either[FileFail, T] =
  try Right(op)
  catch
    case _: NoSuchFileException => Left(FileFail.NotFound(path))
    case e: IOException         => Left(FileFail.Io(e))

/** Writes the lines to the file, creating it or truncating an existing one. */
def writeLines(path: Path, lines: Seq[String]): Either[FileFail, Path] =
  catching(path)(Files.write(path, lines.asJava, StandardCharsets.UTF_8))

/** Appends a line to the file, creating it if needed. */
def appendLine(path: Path, line: String): Either[FileFail, Path] =
  catching(path):
    Files.writeString(
      path,
      line + "\n",
      StandardOpenOption.CREATE,
      StandardOpenOption.APPEND
    )

/** Reads all lines of the file. */
def readLines(path: Path): Either[FileFail, List[String]] =
  catching(path)(Using.resource(Files.lines(path))(_.iterator.asScala.toList))

/** Copies the file next to itself, then renames the copy; returns the final path. */
def copyAndMove(file: Path): Either[FileFail, Path] =
  catching(file):
    val copy = Files.copy(file, file.resolveSibling("copy.txt"))
    Files.move(copy, file.resolveSibling("moved.txt"))

/** Size of the file in bytes. */
def fileSize(path: Path): Either[FileFail, Long] =
  catching(path)(Files.size(path))

/** Sorted file names directly inside the directory. */
def listNames(dir: Path): Either[FileFail, List[String]] =
  catching(dir):
    Using.resource(Files.list(dir))(
      _.iterator.asScala.map(_.getFileName.toString).toList.sorted
    )

/** Runs the body with a temp directory that is deleted afterwards, even on failure. */
def withTempDir[T](body: Path => T): T =
  val dir = Files.createTempDirectory("hello-files")
  try body(dir)
  finally
    Using.resource(Files.walk(dir)): stream =>
      stream.iterator.asScala.toList.reverse.foreach(Files.delete)

@main def fileManipulationDemo(): Unit =
  withTempDir: dir =>
    val file = dir.resolve("sample.txt")
    val result =
      for
        _ <- writeLines(file, List("one", "two"))
        _ <- appendLine(file, "three")
        lines <- readLines(file)
        moved <- copyAndMove(file)
        size <- fileSize(moved)
        names <- listNames(dir)
      yield (lines, size, names)
    result match
      case Right((lines, size, names)) =>
        println(lines)
        println(s"size=$size")
        names.foreach(println)
      case Left(fail) => println(s"failed: $fail")
