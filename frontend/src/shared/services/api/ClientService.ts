import {
    ClientResponse,
    GenericError,
    isGenericError,
    isValidationError,
    PageResponse,
    AddClientRequest,
    ValidationErrorsResponse,
    UpdateClientRequest,
} from "../../types/types";
import Api from "./axios-config/AxiosConfig";

// Função para buscar todos os clientes
const findAllClients = async (params?: {
    searchQuery?: string;
    orderBy?: string;
    orderDirection?: string;
    page?: number;
    size?: number;
}): Promise<PageResponse<ClientResponse> | GenericError> => {
    try {
        const url = "/clients";

        // Cria a URL com os query parameters (orderBy, sortOrder, page, size)
        const queryParams = new URLSearchParams();
        if (params?.searchQuery) queryParams.append("search", params.searchQuery);
        if (params?.orderBy) queryParams.append("orderBy", params.orderBy);
        if (params?.orderDirection)
            queryParams.append("sortOrder", params.orderDirection);
        if (params?.page !== undefined)
            queryParams.append("page", params.page.toString());
        if (params?.size !== undefined)
            queryParams.append("size", params.size.toString());

        const finalUrl = queryParams.toString()
            ? `${url}?${queryParams.toString()}`
            : url;

        // Envia a searchQuery no corpo da requisição
        const response = await Api.get<PageResponse<ClientResponse> | GenericError>(
            finalUrl
        );
        return response.data;
    } catch (error: any) {
        if (error.response) {
            if (isGenericError(error.response.data)) {
                return {
                    message:
                        error.response.data.message ||
                        "Não foi possível recuperar a lista de clientes.",
                    statusCode: error.status,
                } as GenericError;
            }
        }
        console.error("Erro inesperado ao buscar clientes:", error);
        return {
            message:
                "Ocorreu um erro inesperado ao tentar recuperar a lista de clientes do banco de dados.",
            statusCode: 500,
        } as GenericError;
    }
};

// Função para buscar um cliente pelo ID
const findClientById = async (clientId: number) => {
    try {
        const url = `/clients/${clientId}`;
        const response = await Api.get(url);
        return response.data;
    } catch (error: any) {
        if (error.response) {
            if (isGenericError(error.response.data)) {
                return {
                    message:
                        error.response.data.message ||
                        "Não foi possível buscar o cliente.",
                    statusCode: error.status,
                } as GenericError;
            }
        }
        console.error("Erro inesperado ao buscar cliente:", error);
        return {
            message:
                "Ocorreu um erro inesperado ao tentar buscar cliente no banco de dados.",
            statusCode: 500,
        } as GenericError;
    }
};

// Função para deletar um cliente
const deleteClient = async (clientId: number) => {
    try {
        const url = `/clients/${clientId}`;
        await Api.delete(url);
    } catch (error: any) {
        if (error.response) {
            if (isGenericError(error.response.data)) {
                return {
                    message:
                        error.response.data.message ||
                        "Não foi possível deletar o cliente.",
                    statusCode: error.status,
                } as GenericError;
            }
        }
        return {
            message: "Ocorreu um erro inesperado ao tentar deletar o cliente.",
            statusCode: 500,
        } as GenericError;
    }
};

// Função para salvar o cliente
const saveClient = async (
    data: AddClientRequest
): Promise<ClientResponse | GenericError | ValidationErrorsResponse> => {
    try {
        const response = await Api.post<ClientResponse>("/clients", data);
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
                        "Não foi possível salvar o cliente.",
                    statusCode: error.response.status,
                } as GenericError;
            }
        }
        console.error("Erro inesperado ao salvar cliente:", error);
        return {
            message: "Ocorreu um erro inesperado ao tentar salvar o cliente.",
            statusCode: 500,
        } as GenericError;
    }
};

// Função para atualizar cliente
const updateClient = async (
    data: UpdateClientRequest,
    clientId: number
): Promise<ClientResponse | GenericError | ValidationErrorsResponse> => {
    try {
        const url = `/clients/${clientId}`;
        const response = await Api.put(url, data);
        return response.data;
    } catch (error: any) {
        if (error.response) {
            console.error(error.response);
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
                        `Não foi possível atualizar o cadastro do cliente com ID ${clientId}.`,
                    statusCode: error.status,
                } as GenericError;
            }
        }
        console.error("Erro inesperado ao atualizar cliente:", error);
        return {
            message:
                "Ocorreu um erro inesperado ao tentar atualizar o cadastro do cliente.",
            statusCode: 500,
        } as GenericError;
    }
};

export { findAllClients, findClientById, deleteClient, saveClient, updateClient };
