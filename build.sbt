val scala3Version = "3.7.3"

lazy val root = project
  .in(file("."))
  .settings(
    scalacOptions ++= Seq(
      // "-deprecation",
      // "-feature",
      // "-unchecked",
      // "-Xlint:-unused",
      "-Xfatal-warnings"
    ),
    name := "hello-scala3",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    scalacOptions ++= Seq(
      "-Xcheck-macros",
      "-language:experimental.captureChecking",
      "-nowarn"
    ),
    libraryDependencies += "org.scalameta" %% "munit" % "1.2.0" % Test,
    libraryDependencies += "dev.zio" %% "zio-json" % "0.7.44"
  )
