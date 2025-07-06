package com.agencia.backend.presentation.mapper.client;

import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.infrastructure.model.AddressModel;
import com.agencia.backend.presentation.dto.address.AddressDTO;

public interface AddressMapper {
    Address toDomain(AddressDTO dto);

    AddressDTO toDTO(Address domain);

    AddressModel toModel(Address domain);

    Address toDomain(AddressModel model);
}
