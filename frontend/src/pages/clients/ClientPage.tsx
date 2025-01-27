import { Box, Button } from "@mui/material";
import NavigationBar from "../../shared/components/navigation-bar/NavigationBar";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import SearchBar from "../../shared/components/search-bar/SearchBar";
import ActionButton from "../../shared/components/action-button/ActionButton";

// Mock de client ID
const clientID = "1";

// Mock de função para deletar cliente
const handleDeleteClient = (clientID: string) => {
    console.log("Cliente para ser deletado: " + clientID);
}

// Mock de função para editar cliente
const handleEditClient = (clientID: string) => {
    console.log("Client para ser editado: " + clientID);
}

// Mock de função para buscar clientes
const handleSearch = (query: string) => {
    console.log("Buscando por:", query);
    // Adicione lógica de busca (ex.: chamada de API ou filtragem de dados)
};


export default function Clients() {


    return (
        <Box sx={{backgroundColor: "background.default", width: "100vw", height: "100vh"}}>
            <NavigationBar />

            <Box sx={{display: "flex", justifyContent:"space-between", margin: "15px"}}>
                <Box sx={{display: "flex", gap: "15px"}}>
                
                    <SearchBar onSearch={handleSearch} />

                    <ActionButton
                        icon={<DeleteIcon sx={{ mr: 1 }} />}
                        text="DELETAR"
                        hoverColor="#b00020"
                        borderColor="primary.main"
                        iconColor="primary.main"
                        id={clientID}
                        onClick={handleDeleteClient}
                    />

                    <ActionButton
                        icon={<EditIcon sx={{ mr: 1 }} />}
                        text="EDITAR"
                        hoverColor="#0A5C5A"
                        borderColor="#404040"
                        iconColor="primary"
                        id={clientID}
                        onClick={handleEditClient}
                    />
                </Box>

                <Button startIcon={<PersonAddIcon />} disableElevation variant="contained">
                    CADASTRAR CLIENTE
                </Button>
            </Box>
        </Box>
    );
}
