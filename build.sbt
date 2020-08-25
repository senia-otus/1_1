scalaVersion := "2.13.3"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.0"
val CirceVersion = "0.13.0"
val JwtVersion = "4.2.0"
val Log4jVersion = "2.13.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka"         %% "akka-actor-typed"   % AkkaVersion,
  "com.typesafe.akka"         %% "akka-stream"        % AkkaVersion,
  "com.typesafe.akka"         %% "akka-http"          % AkkaHttpVersion,
  "com.typesafe"              % "config"              % "1.4.0",
  "org.flywaydb"              % "flyway-core"         % "6.5.5",
  "org.postgresql"            % "postgresql"          % "42.2.16",
  "org.apache.logging.log4j"  %% "log4j-api-scala"    % "12.0",
  "org.apache.logging.log4j"  % "log4j-api"           % Log4jVersion,
  "org.apache.logging.log4j"  % "log4j-core"          % Log4jVersion % Runtime,
  "io.circe"                  %% "circe-core"         % CirceVersion,
  "io.circe"                  %% "circe-parser"       % CirceVersion,
  "io.circe"                  %% "circe-generic"      % CirceVersion,
  "com.pauldijou"             %% "jwt-core"           % JwtVersion,
  "com.pauldijou"             %% "jwt-circe"          % JwtVersion,
  "de.heikoseeberger"         %% "akka-http-circe"    % "1.31.0",
  "org.scalactic"             %% "scalactic"          % "3.2.0"   % Test,
  "org.scalatest"             %% "scalatest"          % "3.2.0"   % Test,
  "org.scalacheck"            %% "scalacheck"         % "1.14.3"  % Test,
  "org.scalatestplus"         %% "scalacheck-1-14"    % "3.2.0.0" % Test,
  "org.scalamock"             %% "scalamock"          % "5.0.0"   % Test
)