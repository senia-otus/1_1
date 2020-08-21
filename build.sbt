scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.scalactic"     %% "scalactic"       % "3.2.0"   % Test,
  "org.scalatest"     %% "scalatest"       % "3.2.0"   % Test,
  "org.scalacheck"    %% "scalacheck"      % "1.14.3"  % Test,
  "org.scalatestplus" %% "scalacheck-1-14" % "3.2.0.0" % Test,
  "org.scalamock"     %% "scalamock"       % "5.0.0"   % Test
)