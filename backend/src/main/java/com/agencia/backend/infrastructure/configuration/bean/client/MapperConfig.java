package com.agencia.backend.infrastructure.configuration.bean.client;

import com.agencia.backend.presentation.mapper.client.AddressMapper;
import com.agencia.backend.presentation.mapper.client.ClientMapper;
import com.agencia.backend.presentation.mapper.client.PassportMapper;
import com.agencia.backend.presentation.mapper.client.implementation.AddressMapperImp;
import com.agencia.backend.presentation.mapper.client.implementation.ClientMapperImp;
import com.agencia.backend.presentation.mapper.client.implementation.PassportMapperImp;
import com.agencia.backend.presentation.utils.dateConverter.DateConverter;
import com.agencia.backend.presentation.utils.textFormatter.TextFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

  @Bean
  public AddressMapper addressMapper(TextFormatter textFormatter) {
    return new AddressMapperImp(textFormatter);
  }

  @Bean
  public PassportMapper passportMapper(DateConverter dateConverter) {
    return new PassportMapperImp(dateConverter);
  }

  @Bean
  public ClientMapper clientMapper(
      AddressMapper addressMapper,
      PassportMapper passportMapper,
      TextFormatter textFormatter,
      DateConverter dateConverter
  ) {
    return new ClientMapperImp(addressMapper, passportMapper, textFormatter, dateConverter);
  }
}
