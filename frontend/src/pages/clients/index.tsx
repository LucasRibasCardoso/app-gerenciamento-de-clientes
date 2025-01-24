import { Box, Button, Container } from "@mui/material";
import NavigationBar from "../../shared/components/navigation-bar/NavigationBar";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import SearchBar from "../../shared/components/search-bar/SearchBar";
import ActionButton from "../../shared/components/action-button/ActionButton";

export default function Clients() {

    const handleSearch = (query: string) => {
        console.log('Buscando por:', query);
        // Adicione lógica de busca (ex.: chamada de API ou filtragem de dados)
      };

    return (
        <Container maxWidth={false} disableGutters>
            <NavigationBar />

            <Box display={"flex"} justifyContent={"space-between"} m={2}>
                <Box display={"flex"} gap={2}>
                
                    <SearchBar onSearch={handleSearch} />

                    <ActionButton
                        icon={<DeleteIcon sx={{ mr: 1 }} />}
                        text="DELETAR"
                        hoverColor="#FA2A3A"
                        borderColor="#404040"
                        iconColor="primary"
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
        </Container>
    );
}
