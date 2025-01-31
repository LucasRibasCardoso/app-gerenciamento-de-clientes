import { useState } from "react";
import { Box, Backdrop, CircularProgress, Pagination } from "@mui/material";

import NavigationBar from "../../shared/components/navigation-bar/NavigationBar";
import { useGetAllClients, useDeleteClient } from "../../shared/hooks/client/ClientHook";
import ClientsTable from "../../shared/components/table-clients/ClientTable";
import { usePopUp } from "../../shared/context/PopUpContext";
import FunctionalityBar from "../../shared/components/funcionality-bar/FuncionalityBar";

// Mock de função para editar cliente
const handleEditClient = (clientID: string) => {
    console.log("Cliente para ser editado: " + clientID);
};

// Mock de função para criar novo cliente
const handleNewClient = () => {
    console.log("Criar um novo cliente.");
};

export default function Clients() {
    document.title = "Clientes - Client Management";

    const [searchQuery, setSearchQuery] = useState<string>("");
    const [orderBy, setOrderBy] = useState<string>("");
    const [orderDirection, setOrderDirection] = useState<string>("");
    const [selectedClient, setSelectedClient] = useState<string | null>(null);
    const [page, setPage] = useState<number>(1);
    const [size, setSize] = useState<number>(12); // mostra 12 clientes por pagina

    const { showMessage } = usePopUp();

    // Hook com filtros dinâmicos e paginação
    const clients = useGetAllClients({
        searchQuery,
        orderBy,
        orderDirection,
        page: page - 1,
        size,
    });

    // Hook para deletar cliente
    const { mutate: deleteClient, isPending: isDeleting } = useDeleteClient();

    // Abre o popup em caso de ocorrer algum erro
    if (clients.error) {
        showMessage(clients.error.message, "error");
    }

    // Função para mudar de página
    const handlePageChange = (event: React.ChangeEvent<unknown>, newPage: number) => {
        event.preventDefault();
        setPage(newPage);
    };

    return (
        <Box sx={{ backgroundColor: "background.default", width: "100vw", height: "100vh" }}>
            {/* Barra de navegação */}
            <NavigationBar />

            {/* Barra de funcionalidade */}
            <FunctionalityBar
                onSearch={(query) => setSearchQuery(query)}
                orderBy={orderBy}
                onOrderByChange={(value) => setOrderBy(value)}
                orderDirection={orderDirection}
                onOrderDirectionChange={(value) => setOrderDirection(value as "asc" | "desc")}
                selectedClient={selectedClient}
                onDeleteClient={() => {
                    if (selectedClient) deleteClient(Number(selectedClient));
                }}
                onEditClient={() => {
                    if (selectedClient) handleEditClient(selectedClient);
                }}
                onCreateClient={handleNewClient}
            />

            {/* Tabela de clientes */}
            <ClientsTable
                clients={clients.data?.data || []}
                onSelectClient={(id) => setSelectedClient(id)}
            />

            {/* Paginação */}
            <Box sx={{ display: "flex", justifyContent: "center", marginTop: 2 }}>
                <Pagination
                    count={clients.data?.totalPages || 1}
                    page={page}
                    onChange={handlePageChange}
                    color="primary"
                />
            </Box>

            {/* Backdrop para loading */}
            <Backdrop
                sx={(theme) => ({ color: "#fff", zIndex: theme.zIndex.drawer + 1 })}
                open={isDeleting}
            >
                <CircularProgress color="inherit" />
            </Backdrop>
        </Box>
    );
}