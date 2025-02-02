import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";

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

const useSaveClient = () => {
  const queryClient = useQueryClient();
  const { showMessage } = usePopUp();
  const navigate = useNavigate();

  return useMutation<ClientResponse, GenericError | ValidationErrorsResponse, ClientRequest>({
    mutationFn: async (data) => {
      const response = await saveClient(data);
      
      if (isValidationError(response) || isGenericError(response)) {
        console.error(response);
        throw response;
      }
      return response;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({queryKey: "clients"}); 
      navigate("/clientes");
      showMessage("Cliente salvo com sucesso!", "success");
    },
    onError: (error) => {
      showMessage(error.message || "Erro ao salvar cliente.", "error");
    },
  });
};



export { useGetAllClients, useDeleteClient, useSaveClient }