import * as yup from "yup";
import { AddClientRequest, UpdateClientRequest } from "../../../types/types";

// Função para validar datas no formato dd/MM/yyyy
const validateDate = (value: string | null | undefined): boolean => {
    if (!value) return true; // Permite valores nulos
    const [day, month, year] = value.split("/");
    const date = new Date(`${year}-${month}-${day}`);
    return !isNaN(date.getTime());
};

// Função para transformar strings vazias em null
const transformEmptyStringToNull = (value: string | null | undefined): string | null => {
    return value || null;
};

// Schema para Passport
const PassportSchema = yup.object().shape({
    number: yup.string()
        .transform((value) => value || null)
        .nullable(),

    emissionDate: yup.string()
        .nullable()
        .transform(transformEmptyStringToNull)
        .test("is-valid-date", "Data de emissão inválida", validateDate),

    expirationDate: yup.string()
        .nullable()
        .transform(transformEmptyStringToNull)
        .test("is-valid-date", "Data de validade inválida", validateDate),
});

// Schema para Address
const AddressSchema = yup.object().shape({
    zipCode: yup.string()
        .nullable() // Permite valores nulos
        .defined()  
        .transform((value) => value || null), // Converte strings vazias para null

    country: yup.string()
        .nullable() // Permite valores nulos
        .defined()  
        .transform((value) => value || null), // Converte strings vazias para null

    state: yup.string()
        .nullable() // Permite valores nulos
        .defined()  
        .transform((value) => value || null), // Converte strings vazias para null

    city: yup.string()
        .nullable() // Permite valores nulos
        .defined()  
        .transform((value) => value || null), // Converte strings vazias para null

    neighborhood: yup.string()
        .nullable() // Permite valores nulos
        .defined()  
        .transform((value) => value || null), // Converte strings vazias para null

    street: yup.string()
        .nullable() // Permite valores nulos
        .defined()  
        .transform((value) => value || null), // Converte strings vazias para null

    complement: yup.string()
        .nullable() // Permite valores nulos
        .defined()  
        .transform((value) => value || null), // Converte strings vazias para null

    residentialNumber: yup.string()
        .nullable() // Permite valores nulos
        .defined()  
        .transform((value) => value || null), // Converte strings vazias para null
        
})

// Valores padrão para Passport
const PassportDefaultValues = {
    number: null,
    emissionDate: null,
    expirationDate: null,
};

// Valores padrão para Address
const AddressDefaultValues = {
    zipCode: null,
    country: null,
    state: null,
    city: null,
    neighborhood: null,
    street: null,
    complement: null,
    residentialNumber: null,
};

// Schema para cadastro
export const AddClientSchema: yup.SchemaOf<AddClientRequest> = yup.object().shape({
    completeName: yup.string()
        .required("O nome completo é obrigatório")
        .max(100, "O nome completo deve ter no máximo 100 caracteres"),

    cpf: yup.string()
        .required("O CPF é obrigatório")
        .max(14, "O CPF deve ter no máximo 14 caracteres")
        .matches(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/, "CPF inválido"),

    birthDate: yup.string()
        .required("A data de nascimento é obrigatória")
        .matches(/^\d{2}\/\d{2}\/\d{4}$/, "Data de nascimento deve estar no formato dd/MM/yyyy")
        .test("is-valid-date", "Data de nascimento inválida", validateDate),

    phone: yup.string()
        .nullable()
        .transform((value) => value || null) 
        .matches(/\(\d{2}\) \d{4,5}-\d{4}/, "Formato inválido")
        .max(15, "O telefone deve ter no máximo 15 caracteres"),

    email: yup.string()
        .nullable()
        .transform((value) => value || null) 
        .email("E-mail inválido")
        .max(100, "O e-mail deve ter no máximo 100 caracteres"),

    passport: PassportSchema,
    address: AddressSchema,
});

// Valores padrão para o schema AddClient
export const AddClientSchemaDefaultValues = {
    completeName: "",
    cpf: "",
    birthDate: "",
    phone: null,
    email: null,
    passport: PassportDefaultValues,
    address: AddressDefaultValues,
};

// Schema para edição (sem validação do CPF)
export const UpdateClientSchema: yup.SchemaOf<UpdateClientRequest> = yup.object().shape({
    completeName: yup.string()
        .required("O nome completo é obrigatório")
        .max(100, "O nome completo deve ter no máximo 100 caracteres"),
    
    cpf: yup.string()
        .required("O CPF é obrigatório")
        .max(14, "O CPF deve ter no máximo 14 caracteres")
        .matches(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/, "CPF inválido"),

    birthDate: yup.string()
        .required("A data de nascimento é obrigatória")
        .matches(/^\d{2}\/\d{2}\/\d{4}$/, "Data de nascimento deve estar no formato dd/MM/yyyy")
        .test("is-valid-date", "Data de nascimento inválida", validateDate),

    phone: yup.string()
        .nullable()
        .transform((value) => value || null)
        .matches(/\(\d{2}\) \d{4,5}-\d{4}/, "Formato inválido")
        .max(15, "O telefone deve ter no máximo 15 caracteres"),

    email: yup.string()
        .nullable()
        .transform((value) => value || null) 
        .email("E-mail inválido")
        .max(100, "O e-mail deve ter no máximo 100 caracteres"),

    passport: PassportSchema,
    address: AddressSchema
});

// Valores padrão para o schema UpdateClient
export const UpdateClientSchemaDefaultValues = {
    completeName: null,
    cpf: null,
    birthDate: null,
    phone: null,
    email: null,
    passport: {
        number: null,
        emissionDate: null,
        expirationDate: null
    },
    address: {
        zipCode: null,
        country: null,
        state: null,
        city: null,
        neighborhood: null,
        street: null,
        complement: null,
        residentialNumber: null,
    }
}
