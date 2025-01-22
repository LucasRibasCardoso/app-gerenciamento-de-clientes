package com.agencia.backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BackendApplicationTest {

  @Test
  void main() {

    assertDoesNotThrow(() -> new BackendApplication());
  }

}