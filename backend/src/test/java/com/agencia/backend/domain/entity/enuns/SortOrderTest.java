package com.agencia.backend.domain.entity.enuns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SortOrderTest {

  @Test
  void getField_ShouldReturnCorrectFieldValue() {
    assertEquals("asc", SortOrder.ASC.getOrder());
    assertEquals("desc", SortOrder.DESC.getOrder());
  }

  @Test
  void isValid_ShouldReturnTrueForValidValues() {
    assertTrue(SortOrder.isValid("asc"));
    assertTrue(SortOrder.isValid("desc"));
  }

  @Test
  void isValid_ShouldReturnFalseForInvalidValues() {
    assertFalse(SortOrder.isValid("invalidValue"));
    assertFalse(SortOrder.isValid("ascending"));
    assertFalse(SortOrder.isValid(null));
  }
}