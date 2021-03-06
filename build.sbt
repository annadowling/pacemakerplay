name := """pacemakerplay"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions,
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "net.sf.flexjson" % "flexjson" % "3.3",
  "org.mindrot" % "jbcrypt" % "0.3m"
)
