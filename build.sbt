
name := "akka-patterns"

organization := "com.sksamuel.akka"

version := "0.10.0"

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

publishMavenStyle := true

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

publishTo <<= version {
  (v: String) =>
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

parallelExecution in Test := false

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.2.1"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.6.6"

libraryDependencies += "log4j" % "log4j" % "1.2.17" % "test"

libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.6.6" % "test"

libraryDependencies += "commons-io" % "commons-io" % "2.4"

libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5" % "test"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0.M6-SNAP36" % "test"

pomExtra := (
  <url>https://github.com/sksamuel/elastic4s</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:sksamuel/elastic4s.git</url>
      <connection>scm:git@github.com:sksamuel/elastic4s.git</connection>
    </scm>
    <developers>
      <developer>
        <id>theon</id>
        <name>sksamuel</name>
        <url>http://github.com/elastic4s</url>
      </developer>
    </developers>)
