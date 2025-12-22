//val scala3Version = "3.7.3"
val scala3Version = "3.8.0-RC4"

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
    resolvers += Resolver.scalaNightlyRepository,
    scalacOptions ++= Seq(
      "-Xcheck-macros",
      "-language:experimental.captureChecking",
      "-nowarn"
    ),
    libraryDependencies += "org.scalameta" %% "munit" % "1.2.1" % Test,
    libraryDependencies += "dev.zio" %% "zio-json" % "0.8.0"
  )
