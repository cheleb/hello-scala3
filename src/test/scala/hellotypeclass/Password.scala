package hellotypeclass

object Password {

  opaque type Password = String

  def apply(str: String): Password = str

  extension (p: Password) def Show[Password]: String = p

}
