import Api from "../axios-config/AxiosConfig";
import { ClientResponse, GenericError } from "../../../types/types";

// Função para buscar todos os usuários
const findAllUsers = async (): Promise<ClientResponse[] | GenericError> => {
    try {
        const { data } = await Api.get<ClientResponse[] | GenericError>("/users");
        return data;
    } 
    catch (error: any) {
        if (error.response) {
            return {
                message: error.response.data.message || 
                "Não foi possível recuperar a lista de usuários.",
                statusCode: error.response.status || 500,
            } as GenericError;
        }

        // Retorna erro genérico em caso de falha de rede ou erro desconhecido
        return { 
            message: "Ocorreu um erro inesperado ao tentar recuperar a lista de usuários. Verifique sua conexão e tente novamente.", 
            statusCode: 500 
        } as GenericError;
    }
};

// Função para deletar um usuário pelo ID
const deleteUser = async (userId: string): Promise<void | GenericError> => {
    try {
        await Api.delete(`users/${userId}`);
    } 
    catch (error: any) {
        if (error.response) {
            return {
                message: error.response.data.message || `Não foi possível deletar o usuário com ID: ${userId}. Verifique os dados ou tente novamente mais tarde.`,
                statusCode: error.response.status || 500,
            } as GenericError;
        }

        // Retorna erro genérico em caso de falha de rede ou erro desconhecido
        return { message: "`Ocorreu um erro inesperado ao tentar deletar o usuário com ID: ${userId}. Verifique sua conexão e tente novamente.`", statusCode: 500 } as GenericError;
    }
};

export { findAllUsers, deleteUser };