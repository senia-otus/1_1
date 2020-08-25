package ru.otus.sc.config

import com.typesafe.config.{Config, ConfigFactory}

object ApplicationConfig {

  private def initConfigurations(): Config = ConfigFactory.load()

  def loadSettings(): (ApplicationConfig, FlywayConfig, DatabaseConfig) = Some(initConfigurations())
    .map { conf => (
      new ApplicationConfig(
        conf.getString("app.deployment.host"),
        conf.getInt("app.deployment.port")
      ),
      FlywayConfig(conf.getBoolean("flyway.enabled")),
      DatabaseConfig(
        conf.getString("postgres.jdbc"),
        conf.getString("postgres.password"),
        conf.getString("postgres.user")
      )
    )
  }.get

}

case class ApplicationConfig(host: String, port: Int)
case class FlywayConfig(enabled: Boolean)
case class DatabaseConfig(url: String, user: String, password: String)