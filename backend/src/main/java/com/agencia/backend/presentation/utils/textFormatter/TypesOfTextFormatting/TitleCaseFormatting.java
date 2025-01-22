package com.agencia.backend.presentation.utils.textFormatter.TypesOfTextFormatting;

import com.agencia.backend.presentation.utils.textFormatter.TextFormatter;

public class TitleCaseFormatting implements TextFormatter {

  @Override
  public String format(String text) {
    if (text == null || text.isEmpty()) {
      return text;
    }

    StringBuilder titleCase = new StringBuilder();
    boolean capitalizeNext = true;

    for (char c : text.toCharArray()) {
      if (Character.isWhitespace(c)) {
        capitalizeNext = true;
        titleCase.append(c);
      }
      else if (capitalizeNext) {
        titleCase.append(Character.toTitleCase(c));
        capitalizeNext = false;
      }
      else {
        titleCase.append(Character.toLowerCase(c));
      }
    }
    return titleCase.toString();
  }

}
