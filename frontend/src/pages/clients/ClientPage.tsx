import { Box, Button } from "@mui/material";
import NavigationBar from "../../shared/components/navigation-bar/NavigationBar";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import PersonAddIcon from "@mui/icons-material/PersonAdd";

import ActionButton from "../../shared/components/action-button/ActionButton";
import ClientsSearchAndSort from "../../shared/components/clients-search-and-sort/ClientsSearchAndSort";

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

// Mock de função para crir novo cliente
const handleNewClient = () => {
    console.log("Criar um novo cliente.");
}


export default function Clients() {
    document.title = "Clientes - Client Management";
    
    return (
        <Box sx={{backgroundColor: "background.default", width: "100vw", height: "100vh"}}>
            <NavigationBar />

            <Box sx={{display: "flex", justifyContent:"space-between", margin: "15px"}}>
                
                <ClientsSearchAndSort/>
                
                <Box sx={{display: "flex", gap: "15px"}}>
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
        </Box>
    );
}
