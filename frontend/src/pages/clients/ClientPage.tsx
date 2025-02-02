import { useState } from "react";
import { Box, Backdrop, CircularProgress, Pagination, Modal} from "@mui/material";

import { useGetAllClients, useDeleteClient } from "../../shared/hooks/ClientHook";
import { ClientsTable } from "../../shared/components/tables";
import { FunctionalityBar } from "../../shared/components/bars";
import Layout from "../../shared/layouts/Layout";
import { usePopUp } from "../../shared/context/PopUpContext";
import { FormClient } from "../../shared/components/forms";

export default function Clients() {
    document.title = "Clientes - Client Management";

    const { showMessage } = usePopUp();

    const [searchQuery, setSearchQuery] = useState<string>("");
    const [orderBy, setOrderBy] = useState<string>("");
    const [orderDirection, setOrderDirection] = useState<string>("");
    const [selectedClient, setSelectedClient] = useState<string | null>(null);
    const [page, setPage] = useState<number>(1);
    const [size, setSize] = useState<number>(12); // mostra 12 clientes por pagina

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [editingClientId, setEditingClientId] = useState<number | null>(null);

    // Hook com filtros dinâmicos e paginação
    const clients = useGetAllClients({
        searchQuery,
        orderBy,
        orderDirection,
        page: page - 1,
        size,
    });
    // Abre o popup em caso de ocorrer algum erro
    if (clients.error) {
        showMessage(clients.error.message, "error");
    }

    // Hook para deletar cliente
    const { mutate: deleteClient, isPending: isDeleting } = useDeleteClient();

    // Função para mudar de página
    const handlePageChange = (event: React.ChangeEvent<unknown>, newPage: number) => {
        event.preventDefault();
        setPage(newPage);
    };

    // Função para abrir o modal de edição
    const handleEditClient = () => {
        if (selectedClient) {
        setIsEditing(true);
        setEditingClientId(Number(selectedClient));
        setIsModalOpen(true);
        }
    };

    // Função para abrir o modal de criação
    const handleCreateClient = () => {
        setIsEditing(false);
        setEditingClientId(null);
        setIsModalOpen(true);
    };

    // Função para fechar o modal
    const handleCloseModal = () => {
        setIsModalOpen(false);
        setIsEditing(false);
        setEditingClientId(null);
    };

    return (
        <Layout>
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
                onEditClient={handleEditClient}
                onCreateClient={handleCreateClient}
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

            {/* Modal de edição e cadastro */}
            <Modal
                open={isModalOpen}
                onClose={handleCloseModal}
                aria-labelledby="client-form-modal"
                aria-describedby="client-form-modal-description"
            >
                <Box
                sx={{
                    position: "absolute",
                    top: "50%",
                    left: "50%",
                    transform: "translate(-50%, -50%)",
                    width: "90%",
                    maxWidth: "650px",
                    bgcolor: "background.paper",
                    boxShadow: 24,
                    p: 4,
                    borderRadius: 2,
                }}
                >
                <FormClient
                    isOpen={isModalOpen}
                    onClose={handleCloseModal}
                    isEditing={isEditing}
                    clientId={editingClientId}
                />
                </Box>
            </Modal>


        </Layout>
    );
}