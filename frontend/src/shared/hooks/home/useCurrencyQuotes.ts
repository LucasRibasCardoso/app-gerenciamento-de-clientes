import { useQuery } from "@tanstack/react-query";
import { fetchCurrencyQuotes, CurrencyQuote } from "../../services/api/home/CurrencyService";

export const useCurrencyQuotes = () => {
    return useQuery<CurrencyQuote[], Error>({
        queryKey: ["currencyQuotes"], // Chave única para a query
        queryFn: fetchCurrencyQuotes, // Função que faz a requisição
        staleTime: 1000 * 60 * 5, // Dados ficam "frescos" por 5 minutos
        refetchOnWindowFocus: true, // Recarrega os dados quando a janela ganha foco
    });
};