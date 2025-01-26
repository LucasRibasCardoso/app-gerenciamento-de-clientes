import { useMutation, useQuery, useQueryClient  } from "@tanstack/react-query";
import { findAllUsers, deleteUserById } from "../../services/api/user/UserService";
import { ListOfUser } from "../../types/types";

// Hook para deletar um usuário
const useDeleteUser = () => {
    const queryClient = useQueryClient();  // Para acessar o cache do React Query
  
    return useMutation<void, Error, string>({
      mutationFn: deleteUserById,
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: ['users'] });
      },
      onError: (error: any) => {
        const errorMessage = error?.response?.data?.message || error.message || "Erro desconhecido.";
        return errorMessage;
      },
    });
};

// Hook para buscar todos os usuários
const useAllUsers = () => {
    return useQuery<ListOfUser, Error>({
      queryKey: ['users'], // chave para o cache da lista
      queryFn: findAllUsers,
      initialData: [],
    });
  };
export  { useDeleteUser, useAllUsers }
