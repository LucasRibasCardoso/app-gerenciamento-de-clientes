import { Box, Button } from "@mui/material";
import NavigationBar from "../../shared/components/navigation-bar/NavigationBar";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import SearchBar from "../../shared/components/search-bar/SearchBar";
import ActionButton from "../../shared/components/action-button/ActionButton";


export default function Clients() {

    const handleSearch = (query: string) => {
        console.log("Buscando por:", query);
        // Adicione lógica de busca (ex.: chamada de API ou filtragem de dados)
    };

    return (
        <Box 
            sx={{
                bgcolor: "background.default",
                width: "100vw",
                height: "100vh"
            }}
        >
            <NavigationBar />

            <Box 
                sx={{
                    display: "flex",
                    justifyContent:"space-between",
                    margin: "15px"
                }}
            >
                <Box 
                    sx={{
                        display: "flex",
                        gap: "15px"
                    }}
                >
                
                    <SearchBar onSearch={handleSearch} />

                    <ActionButton
                        icon={<DeleteIcon sx={{ mr: 1 }} />}
                        text="DELETAR"
                        hoverColor="#b00020"
                        borderColor="primary.main"
                        iconColor="primary.main"
                    />

                    <ActionButton
                        icon={<EditIcon sx={{ mr: 1 }} />}
                        text="EDITAR"
                        hoverColor="#0A5C5A"
                        borderColor="#404040"
                        iconColor="primary"
                    />
                </Box>

                <Button
                    startIcon={<PersonAddIcon />}
                    disableElevation
                    variant="contained"
                >
                CADASTRAR CLIENTE
                </Button>
            </Box>
        </Box>
    );
}
