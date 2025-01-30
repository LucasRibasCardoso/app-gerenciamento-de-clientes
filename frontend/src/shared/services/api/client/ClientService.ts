import { ClientResponse, GenericError, PaginatedResponse } from "../../../types/types";
import Api from "../axios-config/AxiosConfig";


const findAllClients = async (
    params?: {
      searchQuery?: string;
      orderBy?: string;
      orderDirection?: string;
      page?: number;
      size?: number;
    }
  ): Promise<PaginatedResponse<ClientResponse> | GenericError> => {
    try {
      const url = "/clients";
  
      // Cria a URL com os query parameters (orderBy, sortOrder, page, size)
      const queryParams = new URLSearchParams();
      if (params?.searchQuery) queryParams.append("search", params.searchQuery)
      if (params?.orderBy) queryParams.append("orderBy", params.orderBy);
      if (params?.orderDirection) queryParams.append("sortOrder", params.orderDirection);
      if (params?.page !== undefined) queryParams.append("page", params.page.toString());
      if (params?.size !== undefined) queryParams.append("size", params.size.toString());
  
      const finalUrl = queryParams.toString() ? `${url}?${queryParams.toString()}` : url;
  
      // Envia a searchQuery no corpo da requisição
      const response = await Api.get<PaginatedResponse<ClientResponse> | GenericError>(finalUrl);
  
      return response.data;
    } 
    catch (error: any) {
      if (error.response) {
        return {
          message:
            error.response.data.message ||
            "Não foi possível recuperar a lista de clientes.",
          statusCode: error.response.status || 500,
        } as GenericError;
      }
  
      return {
        message:
          "Ocorreu um erro inesperado ao tentar recuperar a lista de clientes. Verifique sua conexão e tente novamente.",
        statusCode: 500,
      } as GenericError;
    }
  };

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