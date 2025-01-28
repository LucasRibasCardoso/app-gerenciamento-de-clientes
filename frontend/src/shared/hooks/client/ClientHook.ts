import { useQuery } from "@tanstack/react-query";
import { GenericError, ListOfClient } from "../../types/types";
import { findAllClients } from "../../services/api/client/ClientService";

// Hook para buscar todos os clientes
const useGetAllClients = (filters?: { searchQuery?: string; orderBy?: string; orderDirection?: string }) => {
    return useQuery<ListOfClient, GenericError>({
        queryKey: ["clients", filters], // Chave única para cache
        queryFn: async () => {
            const response = await findAllClients(filters);

            if ("statusCode" in response) {
                throw response; // O React Query trata o erro lançado
            }

            return response; // Retorna a lista de clientes em caso de sucesso
        },
    });
};

export { useGetAllClients };
