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
import { useCurrencyQuotes } from "../../hooks/home/useCurrencyQuotes";
import { usePopUp } from "../../context/PopUpContext";

import EuaSVG from "../../assets/images/countriesFlags/estados-unidos.svg";
import CanadaSVG from "../../assets/images/countriesFlags/canada.svg"
import ArgentinaSVG from "../../assets/images/countriesFlags/argentina.svg"; 
import ReinoUnidoSVG from "../../assets/images/countriesFlags/reino-unido.svg"; 
import SuicaSVG from "../../assets/images/countriesFlags/suica.svg"; 
import EuroSVG from "../../assets/images/countriesFlags/uniao-europeia.svg";
import { useCurrencyQuotesMock } from "../../../mocks/useCurrencyQuotesMock";

const flagSVGs: { [key: string]: string } = {
    "USD-BRLT": EuaSVG,
    "USD-BRL": EuaSVG,
    "CAD-BRL": CanadaSVG,
    "ARS-BRL": ArgentinaSVG,
    "GBP-BRL": ReinoUnidoSVG,
    "CHF-BRL": SuicaSVG,
    "EUR-BRL": EuroSVG,
};

const CurrencyTable = () => {
    const { data, isLoading, isError, error } = useCurrencyQuotesMock(); // Alterar entre mock
    const { showMessage } = usePopUp();

    // Exibe o PopUp se houver um erro
    useEffect(() => {
        if (isError) {
            showMessage(error || "Erro ao carregar cotações", "error");
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