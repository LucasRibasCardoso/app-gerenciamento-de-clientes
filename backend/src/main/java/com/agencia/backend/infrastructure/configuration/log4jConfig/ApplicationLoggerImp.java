package com.agencia.backend.infrastructure.configuration.log4jConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLoggerImp implements ApplicationLogger {
  private static final Logger logger = LogManager.getLogger(ApplicationLoggerImp.class);

  @Override
  public void logLogin(String username) {
    ThreadContext.put("username", username);
    ThreadContext.put("action", "LOGIN");
    ThreadContext.put("details", "Login bem-sucedido");

    logger.info("Usuário '{}' realizou login com sucesso", username);

    ThreadContext.clearAll();
  }

  @Override
  public void logAction(String action, String details) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth != null ? auth.getName() : "sistema";

    ThreadContext.put("username", username);
    ThreadContext.put("action", action);
    ThreadContext.put("details", details);

    logger.info("{}: {}", action, details);

    ThreadContext.clearAll();
  }
}
