import React, { useState, ChangeEvent, FormEvent } from "react";
import { IconButton, InputBase, Divider, Box } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";


// Define o tipo das props do componente
interface SearchBarProps {
  placeholder?: string; 
  onSearch?: (query: string) => void; 
}

const SearchBar: React.FC<SearchBarProps> = ({placeholder = "Buscar cliente...",  onSearch}) => {
    const [query, setQuery] = useState<string>(""); // Estado para controlar o texto digitado

    // Lida com o envio do formulário
    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault(); // Previne o comportamento padrão
        if (onSearch) onSearch(query); // Chama a função passada via props
    };

    // Atualiza o estado quando o valor do input muda
    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        setQuery(e.target.value);
    };

    return (
        <Box
            component="form"
            sx={{
                display: "flex",
                alignItems: "center",
                border: "1px, solid, #404040",
                pl: "5px",
                borderRadius: "5px"
            }}
            onSubmit={handleSubmit} // Chama handleSubmit ao enviar o formulário
        >
            {/* Input search */}
            <InputBase
                sx={{ display: "flex" }}
                placeholder={placeholder}
                value={query}
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
