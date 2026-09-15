package cappascap

import util.boundary
import util.boundary.break
import scala.concurrent.Future
import java.io.FileOutputStream
import scala.caps.cap


object BrightFuture {

  class FileSystem

  class Logger(fs: FileSystem^):
   def log(s: String): Unit = () // Write to a log file, using `fs`

  def test(fs: FileSystem^) =
    val l: Logger^{fs} = Logger(fs)
    l.log("hello world!")
    
    val xs: LazyList[Int] =
      LazyList.from(1)
        .map { i =>
          l.log(s"computing elem # $i")
          i * i
        }
    xs

}

class Counter:
  def inc(): Counter = ???

@main
def counter =
  val counter: Counter^ = Counter()
  val counterB: Counter^ = Counter()
  val incrementCaptureAny: () => Counter = () => counter.inc()
  val increment: () ->{counter, counterB} Counter = () => counter.inc()


def usingLogFile[T](op: FileOutputStream^ => T): T =
  val logFile = FileOutputStream("log.txt")
  val result = op(logFile)
  logFile.close()
  result

@main
def iAmFine = 
 usingLogFile: file =>
   file.write('0') 

def testFiles = 
 usingLogFile: file =>
   val capturedFile: () ->{file} Unit = () => file.write('0')
   capturedFile()


// @main
// def burn =
//  val later = usingLogFile: file => 
//   () =>
//      file.write('0') 
//  later() // IOException: Stream closed