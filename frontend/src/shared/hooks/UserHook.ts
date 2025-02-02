import { useMutation, useQuery, useQueryClient  } from "@tanstack/react-query";

import { findAllUsers, deleteUser } from "../services/api/UserService";
import { UserResponse, GenericError, isGenericError } from "../types/types";
import { usePopUp } from "../context/PopUpContext";

// Hook para deletar um usuário
const useDeleteUser = () => {
    const queryClient = useQueryClient();
    const { showMessage } = usePopUp();
  
    return useMutation<void, GenericError, string>({
        mutationFn: async (userId) => {
            const response = await deleteUser(userId);
  
            if (isGenericError(response)) {
                throw response;
            }
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["users"] });
            showMessage("Usuário deletado com sucesso.", "success", );
        },
        onError: (error) => {
            showMessage(error.message || "Erro ao deletar usuário", "error");
            console.error("Erro ao deletar usuário: ", error);
        },
    });
  };
  
  // Hook para buscar todos os usuários
  const useGetAllUsers = () => {
    return useQuery<UserResponse[], GenericError>({
        queryKey: ["users"], // Chave única para cache
        queryFn: async () => {
            const response = await findAllUsers();
  
            if (isGenericError(response)) {
                throw response;
            }
            return response; 
        },
        initialData: []
    });
  };
  export  { useDeleteUser, useGetAllUsers }