import Api from "./axios-config/AxiosConfig";
import {
    UserResponse,
    GenericError,
    isGenericError,
    UserRequest,
    ValidationErrorsResponse,
    isValidationError,
} from "../../types/types";

// Função para buscar todos os usuários
const findAllUsers = async (): Promise<UserResponse[] | GenericError> => {
    try {
        const { data } = await Api.get<UserResponse[] | GenericError>("/users");
        return data;
    } catch (error: any) {
        if (isGenericError(error.response)) {
            return {
                message:
                    error.response.data.message ||
                    "Não foi possível recuperar a lista de usuários.",
                statusCode: error.response.status,
            } as GenericError;
        }

        // Retorna erro genérico em caso de falha de rede ou erro desconhecido
        return {
            message:
                "Ocorreu um erro inesperado ao tentar recuperar a lista de usuários. Verifique sua conexão e tente novamente.",
            statusCode: 500,
        } as GenericError;
    }
};

// Função para deletar um usuário pelo ID
const deleteUser = async (userId: string): Promise<void | GenericError> => {
    try {
        await Api.delete(`users/${userId}`);
    } catch (error: any) {
        if (isGenericError(error.response)) {
            return {
                message:
                    error.response.data.message ||
                    `Não foi possível deletar o usuário com ID: ${userId}. Verifique os dados ou tente novamente mais tarde.`,
                statusCode: error.response.status,
            } as GenericError;
        }

        return {
            message:
                "`Ocorreu um erro inesperado ao tentar deletar o usuário com ID: ${userId}. Verifique sua conexão e tente novamente.`",
            statusCode: 500,
        } as GenericError;
    }
};

// Função para atualizar um usuário pelo ID
const updateUser = async (
    data: UserRequest,
    userId: string
): Promise<UserResponse | GenericError | ValidationErrorsResponse> => {
    try {
        const url = `/users/${userId}`;
        const response = await Api.put<UserResponse>(url, data);
        return response.data;
    } catch (error: any) {
        if (error.response) {
            if (isValidationError(error.response.data)) {
                return {
                    message:
                        error.response.data.message ||
                        "Erro na validação dos campos enviados",
                    statusCode: error.status,
                    errors: new Set(error.response.data.errors),
                } as ValidationErrorsResponse;
            }
            if (isGenericError(error.response.data)) {
                return {
                    message:
                        error.response.data.message ||
                        `Não foi possível atualizar o usuário com ID ${userId}.`,
                    statusCode: error.response.status,
                } as GenericError;
            }
        }
        console.error("Erro inesperado ao atualizar usuário:", error);
        return {
            message: "Ocorreu um erro inesperado ao tentar atualizar o usuário.",
            statusCode: 500,
        } as GenericError;
    }
};

// Função para criar um novo usuário
const createUser = async (
    data: UserRequest
): Promise<UserResponse | GenericError | ValidationErrorsResponse> => {
    try {
        const response = await Api.post<UserResponse>("/users/register", data);
        return response.data;
    } catch (error: any) {
        if (error.response) {
            if (isValidationError(error.response.data)) {
                return {
                    message:
                        error.response.data.message ||
                        "Erro na validação dos campos enviados",
                    statusCode: error.status,
                    errors: new Set(error.response.data.errors),
                } as ValidationErrorsResponse;
            }
            if (isGenericError(error.response.data)) {
                return {
                    message:
                        error.response.data.message ||
                        "Não foi possível criar o usuário.",
                    statusCode: error.response.status,
                } as GenericError;
            }
        }
        console.error("Erro inesperado ao criar usuário:", error);
        return {
            message: "Ocorreu um erro inesperado ao tentar criar o usuário.",
            statusCode: 500,
        } as GenericError;
    }
};

export { findAllUsers, deleteUser, createUser, updateUser };
