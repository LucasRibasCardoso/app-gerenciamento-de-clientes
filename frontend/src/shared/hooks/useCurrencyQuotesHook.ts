import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import { fetchCurrencyQuotes, CurrencyQuote } from "../services/api/CurrencyService";
import { usePopUp } from "../context/PopUpContext";

export const useCurrencyQuotes = () => {
    const { showMessage } = usePopUp();

    const query = useQuery<CurrencyQuote[], Error>({
        queryKey: ["currencyQuotes"],
        queryFn: fetchCurrencyQuotes,
        staleTime: 1000 * 60 * 5, // Atualizado a cada 5 minutos
    });

    // Tratar erros dentro do hook usando useEffect
    useEffect(() => {
        if (query.isError && query.error) {
            showMessage(
                query.error.message ||
                    "Erro inesperado ao tentar buscar as cotações atualizadas",
                "error"
            );
        }
    }, [query.isError, query.error, showMessage]);

    return {
        data: query.data ?? [],
        isLoading: query.isLoading,
        isError: query.isError,
        error: query.error,
        refetch: query.refetch,
    };
};
