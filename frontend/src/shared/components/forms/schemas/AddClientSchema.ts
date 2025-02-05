import * as yup from "yup";

export const AddClientSchema = yup.object().shape({
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
        .test("is-valid-date", "Data de nascimento inválida", (value) => {
            if (!value) return true; // Permite valores nulos
            const [day, month, year] = value.split("/");
            const date = new Date(`${year}-${month}-${day}`);
            return !isNaN(date.getTime()); // Verifica se a data é válida
        }),

    phone: yup.string()
        .nullable()
        .transform((value) => value || null) // Converte strings vazias para null
        .matches(/\(\d{2}\) \d{4,5}-\d{4}/, "Formato inválido")
        .max(15, "O telefone deve ter no máximo 15 caracteres"),

    email: yup.string()
        .nullable()
        .transform((value) => value || null) // Converte strings vazias para null
        .email("E-mail inválido")
        .max(100, "O e-mail deve ter no máximo 100 caracteres"),

    passport: yup.object().shape({
        number: yup.string()
            .nullable()
            .transform((value) => value || null),
        emissionDate: yup.string()
            .nullable()
            .transform((value) => value || null)
            .test("is-valid-date", "Data de emissão inválida", (value) => {
                if (!value) return true; // Permite valores nulos
                const [day, month, year] = value.split("/");
                const date = new Date(`${year}-${month}-${day}`);
                return !isNaN(date.getTime()); // Verifica se a data é válida
            }),
        expirationDate: yup.string()
            .nullable()
            .transform((value) => value || null)
            .test("is-valid-date", "Data de validade inválida", (value) => {
                if (!value) return true; // Permite valores nulos
                const [day, month, year] = value.split("/");
                const date = new Date(`${year}-${month}-${day}`);
                return !isNaN(date.getTime()); // Verifica se a data é válida
            }),
    }),

    address: yup.object().shape({
        zipCode: yup.string()
            .nullable()
            .transform((value) => value || null),
        country: yup.string()
            .nullable()
            .transform((value) => value || null),
        state: yup.string()
            .nullable()
            .transform((value) => value || null),
        city: yup.string()
            .nullable()
            .transform((value) => value || null),
        neighborhood: yup.string()
            .nullable()
            .transform((value) => value || null),
        street: yup.string()
            .nullable()
            .transform((value) => value || null),
        complement: yup.string()
            .nullable()
            .transform((value) => value || null),
        residentialNumber: yup.string()
            .nullable()
            .transform((value) => value || null),
    }),
});

export const AddClientSchemaDefaultValues = {
    completeName: "",
    cpf: "",
    birthDate: "",
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