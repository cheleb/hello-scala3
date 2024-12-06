enum Color:
  case Red, Green, Blue
  case Yellow(dark: Boolean)

val color: Color = Color.Red

@main
def main =
  color match
    case Color.Red          => println("Red")
    case Color.Green        => println("Green")
    case Color.Blue         => println("Blue")
    case Color.Yellow(dark) => println(s"Yellow, dark = $dark")
