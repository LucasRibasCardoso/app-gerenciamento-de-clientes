export type CurrencyKey = "USD-BRLT" | "USD-BRL" | "ARS-BRL" | "GBP-BRL" | "CHF-BRL" | "EUR-BRL" | "CAD-BRL";

export const currencyMock: Record<CurrencyKey, {
    code: string;
    codein: string;
    name: string;
    high: string;
    low: string;
    bid: string;
    pctChange: string;
}> = {
    "USD-BRLT": {
        code: "USD",
        codein: "BRLT",
        name: "Dólar Americano/Real Brasileiro Turismo",
        high: "5.20",
        low: "5.10",
        bid: "5.15",
        pctChange: "0.50",
    },
    "USD-BRL": {
        code: "USD",
        codein: "BRL",
        name: "Dólar Americano/Real Brasileiro",
        high: "5.18",
        low: "5.08",
        bid: "5.13",
        pctChange: "0.30",
    },
    "ARS-BRL": {
        code: "ARS",
        codein: "BRL",
        name: "Peso Argentino/Real Brasileiro",
        high: "0.05",
        low: "0.04",
        bid: "0.045",
        pctChange: "-0.10",
    },
    "GBP-BRL": {
        code: "GBP",
        codein: "BRL",
        name: "Libra Esterlina/Real Brasileiro",
        high: "6.80",
        low: "6.70",
        bid: "6.75",
        pctChange: "0.20",
    },
    "CHF-BRL": {
        code: "CHF",
        codein: "BRL",
        name: "Franco Suíço/Real Brasileiro",
        high: "5.60",
        low: "5.50",
        bid: "5.55",
        pctChange: "-0.05",
    },
    "EUR-BRL": {
        code: "EUR",
        codein: "BRL",
        name: "Euro/Real Brasileiro",
        high: "6.00",
        low: "5.90",
        bid: "5.95",
        pctChange: "0.15",
    },
    "CAD-BRL": {
        code: "CAD",
        codein: "BRL",
        name: "Dólar Canadense/Real Brasileiro",
        high: "4.00",
        low: "3.90",
        bid: "3.95",
        pctChange: "0.10",
    },
};