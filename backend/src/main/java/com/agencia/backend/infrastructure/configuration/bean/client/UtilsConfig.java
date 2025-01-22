package com.agencia.backend.infrastructure.configuration.bean.client;

import com.agencia.backend.presentation.utils.dateConverter.DateConverter;
import com.agencia.backend.presentation.utils.dateConverter.DefaultDateConverter;
import com.agencia.backend.presentation.utils.textFormatter.TextFormatter;
import com.agencia.backend.presentation.utils.textFormatter.TypesOfTextFormatting.TitleCaseFormatting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfig {

  @Bean
  public DateConverter dateConverter() {
    return new DefaultDateConverter();
  }

  @Bean
  public TextFormatter textFormatter() {
    return new TitleCaseFormatting();
  }
}
