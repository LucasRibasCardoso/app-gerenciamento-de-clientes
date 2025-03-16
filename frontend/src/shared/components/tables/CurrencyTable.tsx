import { useEffect } from "react";
import { 
    Table, 
    TableBody, 
    TableCell, 
    TableContainer, 
    TableHead, 
    TableRow, 
    Typography, 
    CircularProgress 
} from "@mui/material";

import { useCurrencyQuotes } from "../../hooks/useCurrencyQuotes";
import { usePopUp } from "../../context/PopUpContext";
import {
    ArgentinaFlag, 
    CanadaFlag, 
    EstadosUnidosFlag, 
    ReinoUnidoFlag, 
    SuicaFlag, 
    UniaoEuropeiaFlag 
} from "../../assets/countriesFlags";

const flagSVGs: { [key: string]: string } = {
    "USD-BRLT": EstadosUnidosFlag,
    "USD-BRL": EstadosUnidosFlag,
    "CAD-BRL": CanadaFlag,
    "ARS-BRL": ArgentinaFlag,
    "GBP-BRL": ReinoUnidoFlag,
    "CHF-BRL": SuicaFlag,
    "EUR-BRL": UniaoEuropeiaFlag,
};

const CurrencyTable = () => {
    const { data, isLoading, isError, error } = useCurrencyQuotes(); // Alterar entre mock
    const { showMessage } = usePopUp();

    // Exibe o PopUp se houver um erro
    useEffect(() => {
        if (isError) {
            showMessage(error?.message || "Erro inesperado ao tentar buscar as cotações atualizadas", "error");
        }
    }, [isError, error, showMessage]);

    // Função para determinar a cor com base na variação
    const getVariationColor = (variation: string) => {
        const variationValue = parseFloat(variation);
        if (variationValue > 0) {
            return "green";
        } 
        else if (variationValue < 0) {
            return "red"; 
        } 
        else {
            return "inherit";
        }
    };

    if (isLoading) {
        return <CircularProgress sx={{ display: "block", margin: "auto", mt: 4 }} />;
    }

    return (
        <TableContainer sx={{ width: "100%", maxWidth: 600}}>
            <Typography variant="h5" sx={{ textAlign: "center", p: 2 }}>
                Principais Cotações (R$)
            </Typography>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Moeda</TableCell>
                        <TableCell>País</TableCell>
                        <TableCell>Compra</TableCell>
                        <TableCell>Variação</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data?.map((quote, index) => (
                        <TableRow key={index} hover>
                            <TableCell sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                                {quote.code}
                                <img
                                    src={flagSVGs[quote.code]}
                                    alt={`Bandeira do ${quote.name}`}
                                    style={{ width: "24px", height: "auto" }}
                                />
                            </TableCell>
                            <TableCell>{quote.name}</TableCell>
                            <TableCell>{quote.bid}</TableCell>
                            <TableCell 
                                sx={{ 
                                    color: getVariationColor(quote.variation),
                                    fontWeight: "bold",
                                }}
                            >
                                {quote.variation}%
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default CurrencyTable;