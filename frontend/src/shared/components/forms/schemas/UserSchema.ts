import * as yup from "yup";
import { AddUserRequest, UserFormData } from "../../../types/types";

const baseSchema = {
    username: yup
        .string()
        .required("Campo obrigatório")
        .max(100, "O nome de usuário deve ter no máximo 100 caracteres")
        .min(6, "O nome de usuário deve ter no mínimo 6 caracteres")
        .matches(/^[A-Za-z]/, "O nome de usuário deve começar com uma letra")
        .matches(
            /^[A-Za-z0-9]+$/,
            "O nome de usuário não pode conter caracteres especiais"
        ),
    role: yup
        .string()
        .required("Campo obrigatório")
        .oneOf(["ADMIN", "USER", "MANAGER"], "Selecione um tipo de usuário válido"),
};

export const AddUserSchema = yup.object({
    ...baseSchema,
    password: yup
        .string()
        .required("Campo obrigatório")
        .min(8, "A senha deve ter no mínimo 8 caracteres")
        .max(20, "A senha deve ter no máximo 20 caracteres")
        .matches(/[^A-Za-z0-9]/, "A senha deve conter pelo menos um caractere especial"),
});

export const UpdateUserSchema = yup.object({
    ...baseSchema,
    password: yup.string().optional(),
});

const defaultValues = {
    username: "",
    role: "USER",
};

export const AddUserSchemaDefaultValues: AddUserRequest = {
    username: defaultValues.username,
    password: "",
    role: defaultValues.role,
};

export const UpdateUserSchemaDefaultValues: UserFormData = {
    username: defaultValues.username,
    role: defaultValues.role,
};
