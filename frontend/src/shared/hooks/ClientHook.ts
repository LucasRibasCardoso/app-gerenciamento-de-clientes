import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";

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
import {
    findAllClients,
    deleteClient,
    saveClient,
    findClientById,
    updateClient,
} from "../services/api/ClientService";
import { usePopUp } from "../context/PopUpContext";

const useDeleteClient = () => {
    const queryClient = useQueryClient();
    const { showMessage } = usePopUp();

    return useMutation<void, GenericError, number>({
        mutationFn: async (clientID) => {
            const response = await deleteClient(clientID);
            if (isGenericError(response)) {
                throw response;
            }
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["clients"] });
            showMessage("Cliente deletado com sucesso", "success");
        },
        onError: (error) => {
            showMessage(error.message || "Erro ao deletar cliente.", "error");
        },
    });
};

const useGetAllClients = (filters?: {
    searchQuery?: string;
    orderBy?: string;
    orderDirection?: string;
    page?: number;
    size?: number;
}) => {
    const { showMessage } = usePopUp();

    const query = useQuery<PageResponse<ClientResponse>, GenericError>({
        queryKey: ["clients", filters],
        queryFn: async () => {
            const response = await findAllClients(filters);

            if (isGenericError(response)) {
                throw response;
            }

            return response;
        },
    });

    // Tratamento de erro dentro do hook
    useEffect(() => {
        if (query.isError && query.error) {
            showMessage(query.error.message || "Erro ao carregar clientes.", "error");
        }
    }, [query.isError, query.error, showMessage]);

    return query;
};

const useGetClientById = (clientId: number) => {
    const { showMessage } = usePopUp();

    const query = useQuery<ClientResponse, GenericError, ClientResponse>({
        queryKey: ["client", clientId],
        queryFn: async ({ queryKey }) => {
            const [, id] = queryKey;

            const response = await findClientById(id as number);

            if (isGenericError(response)) {
                throw response;
            }
            return response;
        },
        enabled: !!clientId,
    });

    // Tratamento de erro dentro do hook
    useEffect(() => {
        if (query.isError && query.error) {
            showMessage(
                query.error.message || "Erro ao carregar dados do cliente.",
                "error"
            );
        }
    }, [query.isError, query.error, showMessage]);

    return query;
};

const useSaveUser = (onClose?: () => void) => {
    const queryClient = useQueryClient();
    const { showMessage } = usePopUp();

    return useMutation<
        ClientResponse,
        GenericError | ValidationErrorsResponse,
        AddClientRequest
    >({
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
                Array.from(error.errors).map((err) => showMessage(err.message, "error"));
            } else {
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
        { clientId: number; data: UpdateClientRequest }
    >({
        mutationFn: async ({ clientId, data }) => {
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
            showMessage(error.message, "error");
        },
    });
};

export {
    useGetAllClients,
    useDeleteClient,
    useSaveUser,
    useGetClientById,
    useUpdateClient,
};
