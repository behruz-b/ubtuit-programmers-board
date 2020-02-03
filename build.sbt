name := "ubtuitProgrammersBoard"

includeFilter in (Assets, LessKeys.less) := "*.less"
excludeFilter in (Assets, LessKeys.less) := "_*.less"

version := "1.0"

val akkaV = "2.5.22"
lazy val `ubtuitprogrammersboard` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

val akkaLibs = Seq(
  "com.typesafe.akka" %% "akka-contrib" % akkaV,
  "com.typesafe.akka" %% "akka-actor" % akkaV,
  "com.typesafe.akka" %% "akka-remote" % akkaV,
  "com.typesafe.akka" %% "akka-testkit" % akkaV,
  "com.github.romix.akka" % "akka-kryo-serialization_2.12" % "0.5.2"
)

val akkaHttp = Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.10",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10"
)

val pgSqlDriver = "org.postgresql" % "postgresql" % "42.0.0"

val dbLibs = Seq(
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.github.tminglei" %% "slick-pg" % "0.15.1",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.15.1",
  "com.opentable.components" % "otj-pg-embedded" % "0.10.0",
  pgSqlDriver
)

val commonDependencies = Seq(
  "com.typesafe.play" %% "play-json" % "2.6.8",
  "com.typesafe.play" %% "play-slick" % "3.0.3",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3",
  "org.joda" % "joda-convert" % "1.8.1"
)

libraryDependencies ++= akkaLibs ++ akkaHttp ++ commonDependencies ++ dbLibs ++ Seq( ws , specs2 % Test , guice,
  "com.typesafe.scala-logging" % "scala-logging_2.12" % "3.7.2",
  "org.apache.logging.log4j" % "log4j-api" % "2.12.1",
  "org.telegram" % "telegrambots" % "4.4.0.1",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "ch.qos.logback" % "logback-core" % "1.1.7",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.21",
  "org.codehaus.janino" % "janino" % "3.0.7",
  //web jars
  "org.webjars" %% "webjars-play" % "2.7.3",
  "org.webjars"% "jquery"% "3.4.1",
  "org.webjars" % "jquery-ui-src" % "1.11.4",
  "org.webjars" % "jquery-file-upload" % "9.10.1",
  "org.webjars" % "knockout" % "3.3.0",
  "org.webjars" % "toastr" % "2.1.2",
  "net.ruippeixotog" %% "scala-scraper" % "2.1.0",
  "org.webjars" % "bootstrap" % "4.3.1",
  "org.webjars" % "bootstrap-select" % "1.13.11" % "runtime",
  "org.webjars.bower" % "popper.js" % "1.12.9",
  "org.webjars" % "momentjs" % "2.8.1"
)