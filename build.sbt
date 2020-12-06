scalaVersion := "2.13.4"

val V = new {
  val cats       = "2.2.0"
  val circe      = "0.14.0-M1"
  val derevo     = "0.11.5"
  val enumeratum = "1.6.0"
  val http4s     = "1.0.0-M4"
  val shapeless  = "2.3.3"
  val pureconfig = "0.14.0"
}

lazy val derevo     = "org.manatki"           %% "derevo-circe" % V.derevo
lazy val shapeless  = "com.chuusai"           %% "shapeless"    % V.shapeless
lazy val pureconfig = "com.github.pureconfig" %% "pureconfig"   % V.pureconfig

lazy val cats = Seq(
  "io.circe" %% "circe-core"    % V.circe,
  "io.circe" %% "circe-generic" % V.circe,
  "io.circe" %% "circe-parser"  % V.circe
)

lazy val circe = Seq(
  "io.circe" %% "circe-core"    % V.circe,
  "io.circe" %% "circe-generic" % V.circe,
  "io.circe" %% "circe-parser"  % V.circe
)

lazy val http4s = Seq(
  "org.http4s" %% "http4s-dsl"          % V.http4s,
  "org.http4s" %% "http4s-blaze-client" % V.http4s,
  "org.http4s" %% "http4s-blaze-server" % V.http4s,
  "org.http4s" %% "http4s-circe"        % V.http4s,
  "org.http4s" %% "http4s-core"         % V.http4s
)

lazy val enumeratum = Seq(
  "com.beachape" %% "enumeratum"       % V.enumeratum,
  "com.beachape" %% "enumeratum-circe" % V.enumeratum
)

libraryDependencies ++= Seq(derevo, shapeless, pureconfig) ++ cats ++ circe ++ http4s ++ enumeratum

scalacOptions := Seq(
  "-Xfatal-warnings",
  "-Ymacro-annotations"
)
