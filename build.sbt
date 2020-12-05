scalaVersion := "2.13.4"

val V = new {
  val cats       = "2.2.0"
  val circe      = "0.14.0-M1"
  val derevo     = "0.11.5"
  val enumeratum = "1.6.0"
  val http4s     = "1.0.0-M4"
  val shapeless  = "2.3.3"
}

libraryDependencies ++= Seq(
  "com.chuusai"   %% "shapeless"           % V.shapeless,
  "com.beachape"  %% "enumeratum"          % V.enumeratum,
  "com.beachape"  %% "enumeratum-circe"    % V.enumeratum,
  "org.manatki"   %% "derevo-circe"        % V.derevo,
  "org.http4s"    %% "http4s-dsl"          % V.http4s,
  "org.http4s"    %% "http4s-blaze-client" % V.http4s,
  "org.http4s"    %% "http4s-blaze-server" % V.http4s,
  "org.http4s"    %% "http4s-circe"        % V.http4s,
  "org.http4s"    %% "http4s-core"         % V.http4s,
  "io.circe"      %% "circe-core"          % V.circe,
  "io.circe"      %% "circe-generic"       % V.circe,
  "io.circe"      %% "circe-parser"        % V.circe,
  "org.typelevel" %% "cats-core"           % V.cats,
  "org.typelevel" %% "cats-effect"         % V.cats
)

scalacOptions := Seq(
  "-Xfatal-warnings",
  "-Ymacro-annotations"
)
