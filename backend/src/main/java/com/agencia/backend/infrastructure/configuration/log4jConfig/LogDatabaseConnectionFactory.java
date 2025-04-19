package com.agencia.backend.infrastructure.configuration.log4jConfig;

import com.agencia.backend.domain.exceptions.global.DatabaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogDatabaseConnectionFactory {
  private static HikariDataSource dataSource;

  @Value("${spring.profiles.active}")
  private String activeProfile;

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String dbUsername;

  @Value("${spring.datasource.password}")
  private String dbPassword;

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @PostConstruct
  public void init() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(dbUrl);
    config.setUsername(dbUsername);
    config.setPassword(dbPassword);
    config.setDriverClassName(driverClassName);
    config.setMaximumPoolSize(3);
    config.setPoolName("LogHikariPool");
    dataSource = new HikariDataSource(config);

    if (activeProfile.equals("dev")) {
      createLogsTableIfNotExists();
    }
  }

  private void createLogsTableIfNotExists() {

    String createTableSQL = new StringBuilder()
        .append("CREATE TABLE IF NOT EXISTS tb_logs (")
        .append("id INT AUTO_INCREMENT PRIMARY KEY, ")
        .append("log_date TIMESTAMP NOT NULL, ")
        .append("log_level VARCHAR(10) NOT NULL, ")
        .append("logger VARCHAR(255) NOT NULL, ")
        .append("message TEXT, ")
        .append("exception TEXT, ")
        .append("username VARCHAR(255), ")
        .append("action VARCHAR(100), ")
        .append("details TEXT)")
        .toString();

    try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
      stmt.execute(createTableSQL);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseException("Erro ao criar tabela de logs");
    }
  }

  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
}
