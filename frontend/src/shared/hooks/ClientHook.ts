import { useMutation, useQuery, useQueryClient  } from "@tanstack/react-query";

import { 
  ClientResponse, 
  ClientRequest,
  GenericError, 
  isGenericError, 
  PageResponse, 
  ValidationErrorsResponse,
  isValidationError
} from "../types/types";
import { findAllClients, deleteClient, saveClient } from "../services/api/ClientService";
import { usePopUp } from "../context/PopUpContext";

const useGetAllClients = (
  filters?: {
    searchQuery?: string;
    orderBy?: string;
    orderDirection?: string;
    page?: number;
    size?: number;
  }
) => {
  return useQuery<PageResponse<ClientResponse>, GenericError>({
    queryKey: ["clients", filters],
    queryFn: async () => {
      const response = await findAllClients(filters);

      if (isGenericError(response)) {
        throw response;        
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
      await deleteClient(clientID);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({queryKey: ["clients"]});
      showMessage("Cliente deletado com sucesso", "success");
    },
    onError: (error) => {
      showMessage(error.message || "Erro ao deletar cliente.", "error");
    }
  });
};

const useSaveClient = (onClose?: () => void) => {
  const queryClient = useQueryClient();
  const { showMessage } = usePopUp();

  return useMutation<ClientResponse, GenericError | ValidationErrorsResponse, ClientRequest>({
    mutationFn: async (data) => {
      const response = await saveClient(data);
      
      if (isValidationError(response) || isGenericError(response)) {
        throw response;
      }
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: "clients" }); // Atualiza a lista de clientes
      showMessage("Cliente salvo com sucesso!", "success"); 
      onClose?.(); 
    },
    onError: (error) => {
      if (isValidationError(error)) {
          Array.from(error.errors)
            .map((err) => showMessage(err.message, "error"))
      } 
      else {
        showMessage(error.message, "error"); // Exibe erros genéricos
      }
    },
  });
};



export { useGetAllClients, useDeleteClient, useSaveClient }