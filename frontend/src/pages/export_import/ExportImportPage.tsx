import { Box, Typography, Card, CardContent } from "@mui/material";

import Layout from "../../shared/layouts/Layout";
import { useGetAllClients } from "../../shared/hooks/ClientHook";
import ExportButton from "../../shared/components/buttons/ExportButton";
import ImportButton from "../../shared/components/buttons/ImportButton";
import { usePopUp } from "../../shared/context/PopUpContext";
import { AddClientRequest } from "../../shared/types/types";
import { useSaveUser } from "../../shared/hooks/ClientHook";

// Interface para representar dados importados da planilha
interface ImportedClientData {
    Nome: string;
    CPF: string;
    Data_Nascimento: string;
    Telefone?: string;
    Email?: string;
    Passaporte?: string;
    Data_Emissão?: string;
    Data_Vencimento?: string | number;
    CEP?: string;
    País?: string;
    Estado?: string;
    Cidade?: string;
    Bairro?: string;
    Rua?: string;
    Complemento?: string;
    Número?: string;
}

// Formatação simplificada para string caso seja número
const formatValue = (value: any): string | null => {
    if (value === undefined || value === null) return null;
    return String(value);
};

export default function ExportImport() {
    document.title = "Importação e Exportação de Dados | Sistema de Gestão de Clientes";
    const { showMessage } = usePopUp();

    // Buscar dados de clientes
    const { data: clientsData } = useGetAllClients({
        size: 1000, // Buscar um número maior para o relatório
    });

    const clients = clientsData?.data || [];

    // Utilizando o hook para salvar clientes
    const saveClientMutation = useSaveUser();

    // Função para importar clientes
    const handleImportClients = (data: any[]) => {
        try {
            // Converte os dados recebidos para o tipo ImportedClientData
            const importedData = data as ImportedClientData[];

            const formattedClients = importedData.map((item): AddClientRequest => {
                return {
                    completeName: item.Nome || "",
                    cpf: item.CPF || "",
                    birthDate: item.Data_Nascimento || "",
                    phone: item.Telefone || null,
                    email: item.Email || null,
                    passport: {
                        number: item.Passaporte || null,
                        emissionDate: formatValue(item.Data_Emissão),
                        expirationDate: formatValue(item.Data_Vencimento),
                    },
                    address: {
                        zipCode: item.CEP || null,
                        country: item.País || null,
                        state: item.Estado || null,
                        city: item.Cidade || null,
                        neighborhood: item.Bairro || null,
                        street: item.Rua || null,
                        complement: item.Complemento || null,
                        residentialNumber: item.Número || null,
                    },
                };
            });

            if (formattedClients.length > 0) {
                showMessage(
                    `${formattedClients.length} clientes prontos para importação.`,
                    "info"
                );

                formattedClients.forEach((client) => {
                    saveClientMutation.mutate(client);
                });
            } else {
                showMessage("Nenhum cliente encontrado no arquivo importado.", "warning");
            }
        } catch (error) {
            showMessage("Erro ao processar os dados importados.", "error");
        }
    };

    // Função para validar dados de clientes importados
    const validateClientData = (data: any[]) => {
        const errors: string[] = [];

        // Converte os dados para o tipo da interface
        const importedData = data as ImportedClientData[];

        // Verificar se os campos obrigatórios estão presentes
        importedData.forEach((item, index) => {
            if (!item.Nome) {
                errors.push(`Linha ${index + 1}: Nome completo é obrigatório`);
            }
            if (!item.CPF) {
                errors.push(`Linha ${index + 1}: CPF é obrigatório`);
            }
            if (!item.Data_Nascimento) {
                errors.push(`Linha ${index + 1}: Data de nascimento é obrigatória`);
            }
        });

        return {
            valid: errors.length === 0,
            errors,
        };
    };

    // Preparar dados para exportação
    const prepareClientsForExport = () => {
        return clients.map((client) => ({
            ID: client.id,
            Nome: client.completeName,
            CPF: client.cpf,
            DataNascimento: client.birthDate,
            Telefone: client.phone || "",
            Email: client.email || "",
            Passaporte: client.passport?.number || "",
            Data_Emissão: client.passport?.emissionDate || "",
            Data_Vencimento: client.passport?.expirationDate || "",
            CEP: client.address?.zipCode || "",
            País: client.address?.country || "",
            Estado: client.address?.state || "",
            Cidade: client.address?.city || "",
            Bairro: client.address?.neighborhood || "",
            Rua: client.address?.street || "",
            Complemento: client.address?.complement || "",
            Número: client.address?.residentialNumber || "",
        }));
    };

    return (
        <Layout>
            <Box sx={{ padding: 3 }}>
                <Box
                    sx={{
                        display: "flex",
                        flexDirection: "column",
                        gap: 5,
                        width: "max-content",
                    }}
                >
                    <Box>
                        <Card sx={{ backgroundColor: "#f5f5f5" }}>
                            <CardContent>
                                <Typography
                                    variant="h5"
                                    gutterBottom
                                >
                                    Exportação de Clientes
                                </Typography>

                                <Typography variant="body1">
                                    Exporte dados do sistema para análise externa ou
                                    backup.
                                </Typography>

                                <Box sx={{ mt: 2, display: "flex", gap: 2 }}>
                                    <ExportButton
                                        data={prepareClientsForExport()}
                                        fileName="clientes_export"
                                        label="Exportar Clientes"
                                    />
                                </Box>
                            </CardContent>
                        </Card>
                    </Box>

                    <Box>
                        <Card sx={{ backgroundColor: "#f5f5f5" }}>
                            <CardContent>
                                <Typography
                                    variant="h5"
                                    gutterBottom
                                >
                                    Importação de Clientes
                                </Typography>

                                <Typography variant="body1">
                                    Importe dados de fontes externas no formato Excel.
                                </Typography>

                                <Box sx={{ mt: 2 }}>
                                    <ImportButton
                                        onImportComplete={handleImportClients}
                                        validator={validateClientData}
                                        label="Importar Clientes"
                                    />
                                </Box>

                                <Typography
                                    variant="caption"
                                    sx={{ mt: 2, display: "block" }}
                                >
                                    Formato esperado: planilha Excel com colunas para:
                                    Nome, CPF, Data de Nascimento, Telefone, Email,
                                    <br />
                                    Número do Passaporte, Data de Emissão, Data de
                                    Vencimento, CEP, País, Estado,
                                    <br />
                                    Cidade, Bairro, Rua, Complemento e Número.
                                </Typography>
                            </CardContent>
                        </Card>
                    </Box>
                </Box>
            </Box>
        </Layout>
    );
}
