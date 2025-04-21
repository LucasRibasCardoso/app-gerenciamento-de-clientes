import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";

import {
    findAllUsers,
    deleteUser,
    updateUser,
    useSaveUser,
    findUserById,
} from "../services/api/UserService";
import {
    UserResponse,
    GenericError,
    isGenericError,
    ValidationErrorsResponse,
    isValidationError,
    UpdateUserRequest,
    AddUserRequest,
} from "../types/types";
import { usePopUp } from "../context/PopUpContext";

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
        },
    });
};

const useGetAllUsers = () => {
    const { showMessage } = usePopUp();

    const query = useQuery<UserResponse[], GenericError>({
        queryKey: ["users"],
        queryFn: async () => {
            const response = await findAllUsers();

            if (isGenericError(response)) {
                throw response;
            }
            return response;
        },
        initialData: [],
    });

    // Tratamento de erro dentro do hook usando useEffect
    useEffect(() => {
        if (query.isError && query.error) {
            showMessage(query.error.message || "Erro ao carregar usuários.", "error");
        }
    }, [query.isError, query.error, showMessage]);

    return query;
};

const useGetUserById = (userId?: string | null) => {
    const { showMessage } = usePopUp();

    const query = useQuery<UserResponse, GenericError, UserResponse>({
        queryKey: ["users", userId],
        queryFn: async ({ queryKey }) => {
            const [, id] = queryKey;
            const response = await findUserById(id as string);

            if (isGenericError(response)) {
                throw response;
            }
            return response;
        },
        enabled: !!userId, // Só executa a query se userId existir
    });

    // Tratamento de erro dentro do hook usando useEffect
    useEffect(() => {
        if (query.isError && query.error) {
            showMessage(
                query.error.message || "Erro ao carregar dados do usuário.",
                "error"
            );
        }
    }, [query.isError, query.error, showMessage]);

    return query;
};

const useCreateUser = (onClose?: () => void) => {
    const queryClient = useQueryClient();
    const { showMessage } = usePopUp();

    return useMutation<
        UserResponse,
        GenericError | ValidationErrorsResponse,
        AddUserRequest
    >({
        mutationFn: async (data) => {
            const response = await useSaveUser(data);

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

const useUpdateUser = (onClose?: () => void) => {
    const queryClient = useQueryClient();
    const { showMessage } = usePopUp();

    return useMutation<
        UserResponse,
        GenericError | ValidationErrorsResponse,
        { userId: string; data: UpdateUserRequest }
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

export { useDeleteUser, useGetAllUsers, useCreateUser, useUpdateUser, useGetUserById };
