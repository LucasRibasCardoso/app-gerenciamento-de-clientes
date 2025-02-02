import axios from "axios";

export interface CurrencyQuote {
    code: string;
    name: string;
    bid: string;
    variation: string;
}

export const fetchCurrencyQuotes = async (): Promise<CurrencyQuote[]> => {
    const API_KEY = import.meta.env.VITE_CURRENCY_API_KEY;

    if (!API_KEY) {
        throw new Error("Chave de API de cotações não encontrada.");
    }

    // Lista de moedas que você deseja buscar
    const currencies = ["USD-BRLT", "USD-BRL", "CAD-BRL", "ARS-BRL", "GBP-BRL", "CHF-BRL", "EUR-BRL"];

    // Constrói a URL com as moedas separadas por vírgula
    const currencyPairs = currencies.join(",");
    const url = `https://economia.awesomeapi.com.br/json/last/${currencyPairs}`;

    try {
        const response = await axios.get(url, {
            headers: {
                "x-api-key": API_KEY, // Envia a chave de API no cabeçalho
            },
        });

        const data = response.data;

        // Mapeia os dados da resposta para o formato esperado
        return currencies.map((currency) => {
            const currencyData = data[currency.replace("-", "")];
            return {
                code: currency,
                name: currencyData.name,
                bid: parseFloat(currencyData.bid).toFixed(3),
                variation: parseFloat(currencyData.pctChange).toFixed(3),
            };
        });
    } catch (error) {
        console.error("Erro ao buscar cotações:", error);
        throw new Error("Erro ao buscar cotações. Verifique a chave de API e a conexão com a internet.");
    }
};