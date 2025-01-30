import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { GenericError, ListOfClient } from "../../types/types";
import { findAllClients, deleteClient } from "../../services/api/client/ClientService";
import { usePopUp } from "../../context/PopUpContext";


// Hook para buscar todos os clientes
const useGetAllClients = (filters?: { searchQuery?: string; orderBy?: string; orderDirection?: string }) => {
  return useQuery<ListOfClient, GenericError>({
    queryKey: ["clients", filters],
    queryFn: async () => {
      const response = await findAllClients(filters);

      if ("statusCode" in response) {
        throw response; // O React Query trata o erro lançado
      }

      return response;
    },
  });
};

const useDeleteClient = () => {
  const queryClient = useQueryClient();
  const { showMessage } = usePopUp();

  return useMutation<void, GenericError, number>({
    mutationFn: async (clientID) => {
      const response = await deleteClient(clientID);

      // Se `result` for um erro (GenericError), lança-o
      if  (response && "statusCode" in response) {
        throw response;
      }
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["clients"] });
      showMessage("Cliente deletado com sucesso", "success");
    },
    onError: (error) => {
      showMessage(error.message || "Erro ao deletar cliente.", "error");
      console.error("Erro ao deletar cliente: ", error);
    }
  });
  
};


export { useGetAllClients, useDeleteClient }