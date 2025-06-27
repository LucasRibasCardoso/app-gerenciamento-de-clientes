package com.agencia.backend.presentation.mapper.client.implementation;

import com.agencia.backend.infrastructure.model.AddressModel;
import com.agencia.backend.presentation.dto.address.AddressDTO;
import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.presentation.mapper.client.AddressMapper;
import com.agencia.backend.presentation.utils.textFormatter.TextFormatter;

public class AddressMapperImp implements AddressMapper {

    private final TextFormatter textFormatter;

    public AddressMapperImp(TextFormatter textFormatter) {
        this.textFormatter = textFormatter;
    }

    @Override
    public Address toDomain(AddressDTO dto) {
        if (dto == null) {
            return new Address(null, null, null, null, null, null, null, null);
        }

        return new Address(dto.zipCode(), textFormatter.format(dto.country()), dto.state(), textFormatter.format(dto.city()), textFormatter.format(dto.neighborhood()), textFormatter.format(dto.street()), dto.complement(), dto.residentialNumber());
    }

    @Override
    public AddressDTO toDTO(Address address) {
        return new AddressDTO(address.getZipCode(), address.getCountry(), address.getState(), address.getCity(), address.getNeighborhood(), address.getStreet(), address.getComplement(), address.getResidentialNumber());
    }

    @Override
    public AddressModel toModel(Address domain) {
        return new AddressModel(domain.getId(), domain.getZipCode(), domain.getCountry(), domain.getState(), domain.getCity(), domain.getNeighborhood(), domain.getStreet(), domain.getComplement(), domain.getResidentialNumber());
    }

    @Override
    public Address toDomain(AddressModel model) {
        return new Address(model.getId(), model.getZipCode(), model.getCountry(), model.getState(), model.getCity(), model.getNeighborhood(), model.getStreet(), model.getComplement(), model.getResidentialNumber());
    }

}
