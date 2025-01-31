import { useEffect, useState } from "react";
import { currencyMock, CurrencyKey } from "./CurrencyMocks";

export interface CurrencyQuote {
    code: string;
    name: string;
    bid: string;
    variation: string;
}

export const useCurrencyQuotesMock = () => {
    const [data, setData] = useState<CurrencyQuote[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isError, setIsError] = useState(false);

    useEffect(() => {
        setTimeout(() => {
            try {
                const currencies: CurrencyKey[] = [
                    "USD-BRLT", 
                    "USD-BRL", 
                    "ARS-BRL", 
                    "GBP-BRL", 
                    "CHF-BRL", 
                    "EUR-BRL", 
                    "CAD-BRL" // Nova moeda
                ];
                const formattedData = currencies.map((currency) => {
                    return {
                        code: currency,
                        name: currencyMock[currency].name,
                        bid: parseFloat(currencyMock[currency].bid).toFixed(3),
                        variation: parseFloat(currencyMock[currency].pctChange).toFixed(3),
                    };
                });
                setData(formattedData);
                setIsLoading(false);
            } catch (error) {
                setIsError(true);
                setIsLoading(false);
            }
        }, 1000);
    }, []);

    return { data, isLoading, isError, error: null };
};