package com.agencia.backend.domain.entity.enuns;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderByFieldTest {

  @Test
  void getField_ShouldReturnCorrectFieldValue() {
    assertEquals("id", OrderByField.ID.getField());
    assertEquals("completeName", OrderByField.COMPLETE_NAME.getField());
  }

  @Test
  void isValid_ShouldReturnTrueForValidValues() {
    assertTrue(OrderByField.isValid("id"));
    assertTrue(OrderByField.isValid("completeName"));
  }

  @Test
  void isValid_ShouldReturnFalseForInvalidValues() {
    assertFalse(OrderByField.isValid("invalidValue"));
    assertFalse(OrderByField.isValid("complete_name"));
    assertFalse(OrderByField.isValid(null));
  }

}