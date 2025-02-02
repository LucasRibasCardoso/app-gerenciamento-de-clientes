import { useQuery } from "@tanstack/react-query";
import { fetchCurrencyQuotes, CurrencyQuote } from "../services/api/CurrencyService";

export const useCurrencyQuotes = () => {
    return useQuery<CurrencyQuote[], Error>({
        queryKey: ["currencyQuotes"],
        queryFn: fetchCurrencyQuotes, 
        staleTime: 1000 * 60 * 5, // Dados ficam "frescos" por 5 minutos
    });
};