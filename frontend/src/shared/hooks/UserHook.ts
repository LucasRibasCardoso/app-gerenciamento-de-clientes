import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

import {
    findAllUsers,
    deleteUser,
    updateUser,
    createUser,
} from "../services/api/UserService";
import {
    UserResponse,
    GenericError,
    isGenericError,
    ValidationErrorsResponse,
    isValidationError,
    UserRequest,
} from "../types/types";
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
            showMessage("Usuário deletado com sucesso.", "success");
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
        initialData: [],
    });
};

// Hook para cadastrar um usuário
const useCreateUser = (onClose?: () => void) => {
    const queryClient = useQueryClient();
    const { showMessage } = usePopUp();

    return useMutation<
        UserResponse,
        GenericError | ValidationErrorsResponse,
        UserRequest
    >({
        mutationFn: async (data) => {
            const response = await createUser(data);

            if (isValidationError(response) || isGenericError(response)) {
                throw response;
            }
            return response;
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["users"] });
            showMessage("Usuário criado com sucesso!", "success");
            onClose?.();
        },
        onError: (error) => {
            if (isValidationError(error)) {
                Array.from(error.errors).map((err) => showMessage(err.message, "error"));
            } else {
                showMessage(error.message, "error");
            }
        },
    });
};

// Hook para editar um usuário
const useUpdateUser = (onClose?: () => void) => {
    const queryClient = useQueryClient();
    const { showMessage } = usePopUp();

    return useMutation<
        UserResponse,
        GenericError | ValidationErrorsResponse,
        { userId: string; data: UserRequest }
    >({
        mutationFn: async ({ userId, data }) => {
            const response = await updateUser(data, userId);

            if (isValidationError(response) || isGenericError(response)) {
                throw response;
            }
            return response;
        },
        onSuccess: (_, variables) => {
            queryClient.invalidateQueries({ queryKey: ["users", variables.userId] });
            queryClient.invalidateQueries({ queryKey: ["users"] });
            showMessage("Usuário atualizado com sucesso!", "success");
            onClose?.();
        },
        onError: (error) => {
            if (isValidationError(error)) {
                Array.from(error.errors).map((err) => showMessage(err.message, "error"));
            } else {
                showMessage(error.message, "error");
            }
        },
    });
};

export { useDeleteUser, useGetAllUsers, useCreateUser, useUpdateUser };
