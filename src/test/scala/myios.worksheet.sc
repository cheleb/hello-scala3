case class MyIOS[A](run: () => A) {
  def map[B](f: A => B): MyIOS[B] = MyIOS(() => f(run()))
  def flatMap[B](f: A => MyIOS[B]): MyIOS[B] = MyIOS(() => f(run()).run())
}

val io = for {
  _ <- MyIOS(() => println("Hello"))
  _ <- MyIOS(() => println("World"))
} yield ()

io.run()
