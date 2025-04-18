import * as yup from "yup";

export const LoginSchema = yup.object({
    username: yup
        .string()
        .required("O usuário é obrigatório.")
        .min(6, "O usuário deve ter no mínimo 6 caracteres.")
        .max(100, "O nome de usuário deve ter no máximo 100 caracteres."),
    password: yup
        .string()
        .required("A senha é obrigatória.")
        .min(8, "A senha deve ter no mínimo 8 caracteres.")
        .max(20, "A senha deve ter no máximo 20 caracteres.")
        .matches(/[^A-Za-z0-9]/, "A senha deve conter pelo menos um caractere especial"),
});
