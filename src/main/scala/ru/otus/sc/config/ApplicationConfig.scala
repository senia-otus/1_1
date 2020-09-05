package ru.otus.sc.config

import com.typesafe.config.{Config, ConfigFactory}

object ApplicationConfig {

  private def initConfigurations(): Config = ConfigFactory.load()

  def loadSettings(): (ApplicationConfig, FlywayConfig, DatabaseConfig) =
    Some(initConfigurations()).map { conf =>
      val dbServer = conf.getString("db.properties.serverName")
      val dbPort   = conf.getString("db.properties.portNumber")
      val dbName   = conf.getString("db.properties.databaseName")

      (
        new ApplicationConfig(
          conf.getString("app.deployment.host"),
          conf.getInt("app.deployment.port")
        ),
        FlywayConfig(conf.getBoolean("flyway.enabled")),
        DatabaseConfig(
          "jdbc:postgresql://" + dbServer + ":" + dbPort + "/" + dbName,
          conf.getString("db.properties.password"),
          conf.getString("db.properties.user")
        )
      )
    }.get

}

case class ApplicationConfig(host: String, port: Int)
case class FlywayConfig(enabled: Boolean)
case class DatabaseConfig(url: String, user: String, password: String)
