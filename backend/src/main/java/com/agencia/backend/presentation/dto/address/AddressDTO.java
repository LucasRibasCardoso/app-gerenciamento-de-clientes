package com.agencia.backend.presentation.dto.address;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressDTO (

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato xxxxx-xxx")
    @Size(max = 9, message = "O CEP deve ter no máximo 9 caracteres")
    String zipCode,

    @NotBlank(message = "O país é obrigatório")
    @Size(max = 100, message = "O país deve ter no máximo 100 caracteres")
    String country,

    @NotBlank(message = "O estado é obrigatório")
    @Size(max = 100, message = "O estado deve ter no máximo 100 caracteres")
    String state,

    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    String city,

    @NotBlank(message = "O bairro é obrigatório")
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    String neighborhood,

    @NotBlank(message = "A rua é obrigatória")
    @Size(max = 100, message = "A rua deve ter no máximo 100 caracteres")
    String street,

    @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres")
    String complement,

    @NotBlank(message = "O número residencial é obrigatório")
    @Size(max = 20, message = "O número residencial deve ter no máximo 20 caracteres")
    String residentialNumber
) {}
