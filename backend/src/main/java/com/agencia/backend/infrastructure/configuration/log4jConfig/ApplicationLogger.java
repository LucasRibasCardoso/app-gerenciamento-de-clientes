package com.agencia.backend.infrastructure.configuration.log4jConfig;

public interface ApplicationLogger {
  void logLogin(String username);
  void logAction(String action, String details);
}
