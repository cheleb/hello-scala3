val scala3Version = "3.6.4"

lazy val root = project
  .in(file("."))
  .settings(
    name := "hello-scala3",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    scalacOptions ++= Seq("-Xcheck-macros"),
    libraryDependencies += "org.scalameta" %% "munit" % "1.1.0" % Test
  )
