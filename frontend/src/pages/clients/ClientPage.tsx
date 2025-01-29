import { useState } from "react";
import { Box, Button } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import PersonAddIcon from "@mui/icons-material/PersonAdd";

import NavigationBar from "../../shared/components/navigation-bar/NavigationBar";
import ActionButton from "../../shared/components/action-button/ActionButton";
import SearchBar from "../../shared/components/search-bar/SearchBar";
import SelectButton from "../../shared/components/select-button/SelectButton";
import { useGetAllClients } from "../../shared/hooks/client/ClientHook";
import ClientsTable from "../../shared/components/table-clients/ClientTable";

// Mock de função para deletar cliente
const handleDeleteClient = (clientID: string) => {
    console.log("Cliente para ser deletado: " + clientID);
};

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
    const [selectedClient, setSelectedClient] = useState<string | null>(null); // Estado para cliente selecionado

    // Hook com filtros dinâmicos
    const clients = useGetAllClients({
        searchQuery,
        orderBy,
        orderDirection,
    });

    return (
        <Box sx={{ backgroundColor: "background.default", width: "100vw", height: "100vh" }}>
            {/* Barra de navegação */}
            <NavigationBar />

            {/* Barra de funcionalidade */}
            <Box sx={{ display: "flex", justifyContent: "space-between", margin: "15px", height: "45px" }}>
                <Box sx={{ display: "flex", gap: "15px" }}>
                    {/* Barra de pesquisa */}
                    <SearchBar onSearch={(query) => setSearchQuery(query)} />

                    {/* Seleção de campo de ordenação */}
                    <SelectButton
                        label="Ordenar"
                        value={orderBy}
                        options={[
                            { value: "id", label: "Recente Adicionado" },
                            { value: "completeName", label: "Nome" },
                        ]}
                        onChange={(value) => setOrderBy(value)}
                    />

                    {/* Seleção de direção da ordenação */}
                    <SelectButton
                        label="Direção"
                        value={orderDirection}
                        options={[
                            { value: "asc", label: "Crescente" },
                            { value: "desc", label: "Decrescente" },
                        ]}
                        onChange={(value) => setOrderDirection(value as "asc" | "desc")}
                    />
                </Box>

                <Box sx={{ display: "flex", gap: "15px" }}>
                    <ActionButton
                        icon={<DeleteIcon sx={{ mr: 1 }} />}
                        text="DELETAR"
                        hoverColor="#b00020"
                        borderColor="primary.main"
                        iconColor="primary.main"
                        onClick={() => {
                            if (selectedClient) handleDeleteClient(selectedClient);
                        }}
                    />

                    <ActionButton
                        icon={<EditIcon sx={{ mr: 1 }} />}
                        text="EDITAR"
                        hoverColor="#0A5C5A"
                        borderColor="#404040"
                        iconColor="primary"
                        onClick={() => {
                            if (selectedClient) handleEditClient(selectedClient);
                        }}
                    />

                    <Button
                        startIcon={<PersonAddIcon />}
                        disableElevation
                        variant="contained"
                        onClick={handleNewClient}
                    >
                        CADASTRAR CLIENTE
                    </Button>
                </Box>
            </Box>

            {/* Tabela de clientes */}
            <ClientsTable
                clients={clients.data || []}
                onSelectClient={(id) => setSelectedClient(id)} 
            />
        </Box>
    );
}
