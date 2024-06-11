package hellotypeclass

object password {

  opaque type Password = String

  def apply(str: String): Password = str

  extension (p: Password) def show[Password]: String = "******"

}
