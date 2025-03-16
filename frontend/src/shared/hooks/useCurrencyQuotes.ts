import { useQuery } from "@tanstack/react-query";
import { fetchCurrencyQuotes, CurrencyQuote } from "../services/api/CurrencyService";

export const useCurrencyQuotes = () => {
    const { data, isLoading, isError, error, refetch } = useQuery<CurrencyQuote[], Error>({
        queryKey: ["currencyQuotes"],
        queryFn: fetchCurrencyQuotes,
        staleTime: 1000 * 60 * 5, // Atualizado a cada 5 minutos
    });

    return {
        data: data ?? [],
        isLoading,
        isError,
        error,
        refetch,
    };
};