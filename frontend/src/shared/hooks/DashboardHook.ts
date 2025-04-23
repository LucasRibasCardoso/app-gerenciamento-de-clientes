import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import { DashboardResponse, GenericError, isGenericError } from "../types/types";
import { GetDashboardData } from "../services/api/DashboardService";
import { usePopUp } from "../context/PopUpContext";

export const useDashboardData = () => {
    const { showMessage } = usePopUp();

    const query = useQuery<DashboardResponse, GenericError>({
        queryKey: ["dashboard"],
        queryFn: async () => {
            const response = await GetDashboardData();

            if (isGenericError(response)) {
                throw response;
            }
            console.log("Dashboard data:", response);
            return response;
        },
        staleTime: 5 * 60 * 1000, // Cache válido por 5 minutos
    });

    // Usar useEffect para lidar com erros
    useEffect(() => {
        if (query.error) {
            showMessage(
                query.error.message || "Erro ao carregar dados do dashboard",
                "error"
            );
        }
    }, [query.error, showMessage]);

    return query;
};
