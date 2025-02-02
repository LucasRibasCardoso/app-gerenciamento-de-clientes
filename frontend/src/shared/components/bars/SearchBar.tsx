import React, { useState, ChangeEvent, FormEvent } from "react";
import { IconButton, InputBase, Divider, Box } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";


// Define o tipo das props do componente
interface SearchBarProps {
  placeholder?: string; 
  onSearch?: (query: string) => void; 
}

const SearchBar: React.FC<SearchBarProps> = ({placeholder = "Buscar cliente...",  onSearch}) => {
    const [search, setSearch] = useState<string>("");
    
    // Lida com o envio do formulário
    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault(); 
        if (onSearch) onSearch(search);
    };

    // Atualiza o estado quando o valor do input muda
    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        setSearch(e.target.value);
    };

    return (
        <Box
            component="form"
            sx={{
                display: "flex",
                alignItems: "center",
                border: "1px, solid, #404040",
                padding: "5px",
                borderRadius: "5px",
                height: "45px",
            }}
            onSubmit={handleSubmit}
        >
            {/* Input search */}
            <InputBase
                sx={{ display: "flex", minWidth: "200px" }}
                placeholder={placeholder}
                value={search}
                onChange={handleChange} 
                inputProps={{ "aria-label": placeholder }}
            />

            <Divider sx={{ height: "30px", backgroundColor: "primary.main"}} orientation="vertical" />
            
             {/* Botão para envio do forms */}
            <IconButton type="submit" aria-label="Pesquisar">
                <SearchIcon sx={{ color: "primary.main" }} />
            </IconButton>
        </Box>
    );
};

export default SearchBar;
