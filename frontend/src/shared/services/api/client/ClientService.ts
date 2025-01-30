import { GenericError, ListOfClient } from "../../../types/types";
import Api from "../axios-config/AxiosConfig";


// Função para buscar todos os usuários
const findAllClients = async (params?: {searchQuery?: string; orderBy?: string; orderDirection?: string }): Promise<ListOfClient | GenericError> => {
    try {
        const url = "/clients";
        const queryParams = new URLSearchParams();

        if (params?.searchQuery) queryParams.append("search", params.searchQuery);
        if (params?.orderBy) queryParams.append("orderBy", params.orderBy);
        if (params?.orderDirection) queryParams.append("sortOrder", params.orderDirection);

        const finalUrl = queryParams.toString() ? `${url}?${queryParams.toString()}` : url;

        const { data } = await Api.get<ListOfClient | GenericError>(finalUrl);
        return data;
    }
    catch (error : any) {
        if (error.response) {
            return {
                message: error.response.data.message || 
                "Não foi possível recuperar a lista de clientes.",
                statusCode: error.response.status || 500,
            } as GenericError;
        }

        return { 
            message: "Ocorreu um erro inesperado ao tentar recuperar a lista de clientes. Verifique sua conexão e tente novamente.", 
            statusCode: 500 
        } as GenericError;
    }
};

// Função para deletar um cliente
const deleteClient = async (clientId: number) => {
    try {
        const url = `/clients/${clientId}`;
        await Api.delete(url);
    }
    catch (error: any) {
        if (error.response){
            return {
                message: error.response.data.message || 
                "Não foi possível deletar o cliente.",
                statusCode: error.response.status || 500,
            } as GenericError;
        }
        return { 
            message: "Ocorreu um erro inesperado ao tentar deletar o cliente. Verifique sua conexão e tente novamente.", 
            statusCode: 500 
        } as GenericError;
    }
};

export { findAllClients, deleteClient };