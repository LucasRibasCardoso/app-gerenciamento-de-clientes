import * as yup from "yup";
import { AddClientRequest, UpdateClientRequest } from "../../../types/types";

// Função para validar datas no formato dd/MM/yyyy
const validateDate = (value: string | null | undefined): boolean => {
    if (!value) return true;
    const [day, month, year] = value.split("/");
    const date = new Date(`${year}-${month}-${day}`);
    return !isNaN(date.getTime());
};

// Schema para Passport
const PassportSchema = yup.object().shape({
    number: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        ),
    emissionDate: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        )
        .test("date", "Data de emissão inválida", validateDate),
    expirationDate: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        )
        .test("date", "Data de validade inválida", validateDate)
});

// Schema para Address
const AddressSchema = yup.object().shape({
    zipCode: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        ),
    country: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        ),
    state: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        ),
    city: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        ),
    neighborhood: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        ),
    street: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        ),
    complement: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        ),
    residentialNumber: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        )
});

// Schema base dos campos comuns
const baseSchema = {
    completeName: yup.string()
        .required("O nome completo é obrigatório")
        .max(100, "O nome completo deve ter no máximo 100 caracteres"),
    birthDate: yup.string()
        .required("A data de nascimento é obrigatória")
        .matches(/^\d{2}\/\d{2}\/\d{4}$/, "Data de nascimento deve estar no formato dd/MM/yyyy")
        .test("date", "Data de nascimento inválida", validateDate),
    phone: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        )
        .matches(/\(\d{2}\) \d{4,5}-\d{4}/, "Formato de telefone inválido")
        .max(15, "O telefone deve ter no máximo 15 caracteres")
        .default(null),
    email: yup.string()
        .nullable()
        .transform((value, originalValue) =>
            originalValue === undefined || originalValue === "" ? null : value
        )
        .email("E-mail inválido")
        .max(100, "O e-mail deve ter no máximo 100 caracteres")
        .default(null),
    passport: PassportSchema,
    address: AddressSchema
};

// Schema para cadastro (cpf requerido)
export const AddClientSchema = yup.object({
    ...baseSchema,
    cpf: yup.string()
        .required("O CPF é obrigatório")
        .matches(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/, "CPF inválido")
}).required();

// Schema para atualização (cpf opcional)
export const UpdateClientSchema = yup.object({
    ...baseSchema,
    cpf: yup.string()
        .matches(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/, "CPF inválido")
        .optional()
}).required();

// Valores padrão compartilhados
const defaultValues = {
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
        residentialNumber: null
    }
};

// Valores padrão para novo cliente
export const AddClientSchemaDefaultValues: AddClientRequest = {
    completeName: "",
    cpf: "",
    birthDate: "",
    phone: null,
    email: null,
    passport: defaultValues.passport,
    address: defaultValues.address
};

// Valores padrão para atualização de cliente
export const UpdateClientSchemaDefaultValues: UpdateClientRequest = {
    completeName: "",
    birthDate: "",
    phone: null,
    email: null,
    passport: defaultValues.passport,
    address: defaultValues.address
};
