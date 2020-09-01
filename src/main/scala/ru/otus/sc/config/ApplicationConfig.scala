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
        conf.getString(
          "jdbc:postgresql://%s:%s/%s"
            .format(conf.getString("db.properties.serverName"))
            .format(conf.getString("db.properties.portNumber"))
            .format(conf.getString("db.properties.databaseName"))
        ),
        conf.getString("db.properties.password"),
        conf.getString("db.properties.user")
      )
    )
  }.get

}

case class ApplicationConfig(host: String, port: Int)
case class FlywayConfig(enabled: Boolean)
case class DatabaseConfig(url: String, user: String, password: String)