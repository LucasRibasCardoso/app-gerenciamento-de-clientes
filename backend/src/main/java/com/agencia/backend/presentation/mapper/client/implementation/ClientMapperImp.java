package com.agencia.backend.presentation.mapper.client.implementation;

import com.agencia.backend.infrastructure.model.AddressModel;
import com.agencia.backend.infrastructure.model.PassportModel;
import com.agencia.backend.presentation.dto.address.AddressDTO;
import com.agencia.backend.presentation.dto.client.ClientRequestDTO;
import com.agencia.backend.presentation.dto.client.ClientRequestUpdateDTO;
import com.agencia.backend.presentation.dto.client.ClientResponseDTO;
import com.agencia.backend.presentation.dto.passport.PassportDTO;
import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.infrastructure.model.ClientModel;
import com.agencia.backend.presentation.mapper.client.AddressMapper;
import com.agencia.backend.presentation.mapper.client.ClientMapper;
import com.agencia.backend.presentation.mapper.client.PassportMapper;
import com.agencia.backend.presentation.utils.dateConverter.DateConverter;
import com.agencia.backend.presentation.utils.textFormatter.TextFormatter;

public class ClientMapperImp implements ClientMapper {

    private final AddressMapper addressMapper;
    private final PassportMapper passportMapper;
    private final TextFormatter textFormatter;
    private final DateConverter dateConverter;

    public ClientMapperImp(
            AddressMapper addressMapper,
            PassportMapper passportMapper,
            TextFormatter textFormatter,
            DateConverter dateConverter
    ) {
        this.addressMapper = addressMapper;
        this.passportMapper = passportMapper;
        this.textFormatter = textFormatter;
        this.dateConverter = dateConverter;
    }

    @Override
    public Client toDomain(ClientRequestDTO dto) {
        Passport passport = passportMapper.toDomain(dto.passport());
        Address address = addressMapper.toDomain(dto.address());

        return new Client(
                null,
                textFormatter.format(dto.completeName()),
                dto.cpf(),
                dateConverter.convertToLocalDate(dto.birthDate()),
                dto.phone(),
                dto.email(),
                passport,
                address
        );
    }

    @Override
    public Client toDomain(ClientModel model) {
        Passport passport = passportMapper.toDomain(model.getPassport());
        Address address = addressMapper.toDomain(model.getAddress());

        return new Client(
                model.getId(),
                model.getCompleteName(),
                model.getCpf(),
                model.getBirthDate(),
                model.getPhone(),
                model.getEmail(),
                passport,
                address
        );
    }

    @Override
    public Client toDomain(ClientRequestUpdateDTO dto) {
        Passport passport = passportMapper.toDomain(dto.passport());
        Address address = addressMapper.toDomain(dto.address());

        return new Client(
                null,
                textFormatter.format(dto.completeName()),
                null, // Campo que não pode ser atualizado (cpf)
                dateConverter.convertToLocalDate(dto.birthDate()),
                dto.phone(),
                dto.email(),
                passport,
                address
        );
    }

    @Override
    public ClientResponseDTO toDTO(Client domain) {
        PassportDTO passport = passportMapper.toDTO(domain.getPassport());
        AddressDTO address = addressMapper.toDTO(domain.getAddress());

        return new ClientResponseDTO(
                domain.getId(),
                domain.getCompleteName(),
                domain.getCpf(),
                dateConverter.convertToString(domain.getBirthDate()),
                domain.getPhone(), domain.getEmail(),
                passport,
                address
        );
    }

    @Override
    public ClientModel toModel(Client domain) {
        PassportModel passportModel = passportMapper.toModel(domain.getPassport());
        AddressModel addressModel = addressMapper.toModel(domain.getAddress());

        return new ClientModel(
                domain.getId(),
                domain.getCompleteName(),
                domain.getCpf(),
                domain.getBirthDate(),
                domain.getPhone(),
                domain.getEmail(),
                passportModel,
                addressModel
        );
    }

}
