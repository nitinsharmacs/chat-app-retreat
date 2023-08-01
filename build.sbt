ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "chat-app-retreat",
    libraryDependencies ++= Seq(
      "com.linecorp.armeria" %% "armeria-scala" % "1.24.2"
    )
  )
