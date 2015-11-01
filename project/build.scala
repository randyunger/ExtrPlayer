import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._
//import sbtassembly.AssemblyKeys._
import sbtassembly.AssemblyPlugin.autoImport._

object ExtrBuild extends Build {
  val Organization = "org.runger.extrplayr"
  val Name = "ExtrPlayer"
  val Version = "0.1.1"
  val ScalaVersion = "2.11.6"
  val ScalatraVersion = "2.4.0-RC2-2"
  val jettyVersion = "9.3.5.v20151012"

//  lazy val commonSettings = Seq(
//    version := "0.1-SNAPSHOT",
//    organization := "com.example",
//    scalaVersion := "2.10.1"
//  )

  test in assembly := {}

  mainClass in assembly := Some("org.runger.extrplayr.WebServerRunner")

  lazy val project = Project (
    "ExtrPlayer",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      libraryDependencies ++= Seq(
        "org.scala-lang" % "scala-library" % ScalaVersion
        ,"org.scala-lang" % "scala-reflect" % ScalaVersion
        ,"org.scala-lang" % "scala-compiler" % ScalaVersion
        ,"org.scalatra" %% "scalatra" % ScalatraVersion
        ,"org.scalatra" %% "scalatra-scalate" % ScalatraVersion
        ,"org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test"
        ,"org.eclipse.jetty" % "jetty-util" % jettyVersion
        ,"org.eclipse.jetty" % "jetty-webapp" % jettyVersion
        ,"org.eclipse.jetty" % "jetty-server" % jettyVersion
        ,"org.eclipse.jetty" % "jetty-servlet" % jettyVersion
        ,"ch.qos.logback" % "logback-classic" % "1.1.2" % "runtime"
        ,"javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
        ,"commons-net" % "commons-net" % "2.0"
        ,"com.typesafe.akka" % "akka-actor_2.11" % "2.3.11"
        ,"com.typesafe.akka" % "akka-testkit_2.11" % "2.3.11" % "test"
        ,"org.scala-lang.modules" %% "scala-xml" % "1.0.3"
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}


/*
  "org.scala-lang" % "scala-library" % "2.11.6"
  ,"org.scalatra" %% "scalatra" % "2.4.0-RC2-2"
  ,"org.scalatra" %% "scalatra-scalate" % "2.4.0-RC2-2"
  ,"commons-net" % "commons-net" % "2.0"
  ,"com.typesafe.akka" % "akka-actor_2.11" % "2.3.11"
  ,"com.typesafe.akka" % "akka-testkit_2.11" % "2.3.11" % "test"
  ,"org.scala-lang.modules" %% "scala-xml" % "1.0.3"
  ,"org.mortbay.jetty" % "jetty-util" % "6.1.26" % "compile"
  ,"org.mortbay.jetty" % "jetty" % "6.1.26" % "compile"
  ,"org.eclipse.jetty" % "jetty-server" % "9.3.4.RC0"
  ,"org.eclipse.jetty" % "jetty-webapp" % "9.3.4.RC0"
//  ,"org.eclipse.jetty" % "jetty-server" % "7.6.8.v20121106"
//  ,"org.eclipse.jetty" % "jetty-server" % "9.3.3.v20150827"
//  ,"org.eclipse.jetty" %% "jetty-webapp" % "8.1.8.v20121106" % "container"
//  ,"org.eclipse.jetty.orbit" %% "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))

 */