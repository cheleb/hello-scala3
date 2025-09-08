val scala3Version = "3.7.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "hello-scala3",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    scalacOptions ++= Seq(
      "-Xcheck-macros",
      "-language:experimental.captureChecking",
      "-nowarn"
    ),
    libraryDependencies += "org.scalameta" %% "munit" % "1.1.1" % Test
  )
