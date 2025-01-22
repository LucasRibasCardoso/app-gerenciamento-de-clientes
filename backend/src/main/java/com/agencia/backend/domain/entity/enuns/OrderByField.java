package com.agencia.backend.domain.entity.enuns;

public enum OrderByField {
  ID("id"), COMPLETE_NAME("completeName");

  private final String field;

  OrderByField(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }

  public static boolean isValid(String value) {
    for (OrderByField field : OrderByField.values()) {
      if (field.getField().equals(value)) {
        return true;
      }
    }
    return false;
  }
}
