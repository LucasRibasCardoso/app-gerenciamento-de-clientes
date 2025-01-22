package com.agencia.backend.domain.entity.enuns;

public enum SortOrder {
  ASC("asc"), DESC("desc");

  private final String order;

  SortOrder(String order) {
    this.order = order;
  }

  public String getOrder() {
    return order;
  }

  public static boolean isValid(String value) {
    for (SortOrder sort : SortOrder.values()) {
      if (sort.getOrder().equals(value)) {
        return true;
      }
    }
    return false;
  }
}
