import { useMutation, useQuery, useQueryClient  } from "@tanstack/react-query";

import { 
  ClientResponse, 
  AddClientRequest,
  GenericError, 
  isGenericError, 
  PageResponse, 
  ValidationErrorsResponse,
  isValidationError,
  UpdateClientRequest,
} from "../types/types";
import { findAllClients, deleteClient, saveClient, getClientById, updateClient } from "../services/api/ClientService";
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

const useGetClientById = (clientId: number) => {
  return useQuery<ClientResponse, GenericError, ClientResponse>({
    queryKey: ["client", clientId],
    queryFn: async ({ queryKey }) => {
      const [, id] = queryKey;
      const response = await getClientById(id as number);
      return response;
    },
    enabled: !!clientId, // Só executa a query se clientId existir
  });
};

const useDeleteClient = () => {
  const queryClient = useQueryClient();
  const { showMessage } = usePopUp();

  return useMutation<void, GenericError, number>({
    mutationFn: async (clientID) => {
      const response = await deleteClient(clientID);
      if (isGenericError(response)){
        throw response;
      }
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

const useAddClient = (onClose?: () => void) => {
  const queryClient = useQueryClient();
  const { showMessage } = usePopUp();

  return useMutation<ClientResponse, GenericError | ValidationErrorsResponse, AddClientRequest>({
    mutationFn: async (data) => {
      const response = await saveClient(data);
      
      if (isValidationError(response) || isGenericError(response)) {
        throw response;
      }
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["clients"] }); // Atualiza a lista de clientes
      showMessage("Cliente salvo com sucesso!", "success"); 
      onClose?.(); 
    },
    onError: (error) => {
      if (isValidationError(error)) {
          Array.from(error.errors)
            .map((err) => showMessage(err.message, "error"))
      } 
      else {
        showMessage(error.message, "error");
      }
    },
  });
};

const useUpdateClient = (onClose?: () => void) => {
  const queryClient = useQueryClient();
  const { showMessage } = usePopUp();

  return useMutation<
    ClientResponse, 
    GenericError | ValidationErrorsResponse, 
    {clientId: number; data: UpdateClientRequest}
  >({
    mutationFn: async ({clientId, data}) => {
      const response = await updateClient(data, clientId);
      
      if (isValidationError(response) || isGenericError(response)) {
        throw response;
      }
      return response;
    },
    onSuccess: (clientId) => {
      queryClient.invalidateQueries({ queryKey: ["clients", clientId] });
      queryClient.invalidateQueries({ queryKey: ["clients"] });
      showMessage("Cliente atualizado com sucesso!", "success");
      onClose?.(); 
    },
    onError: (error) => {
      showMessage(error.message , "error");
    },
  });
};

export { useGetAllClients, useDeleteClient, useAddClient, useGetClientById, useUpdateClient };