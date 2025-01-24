import React, { useState, ChangeEvent, FormEvent } from 'react';
import { IconButton, InputBase, Divider, Box } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

// Estilos centralizados
const styles = {
    form: {
        p: '2px 4px',
        display: 'flex',
        alignItems: 'center',
        border: 1,
        borderColor: '#404040',
        borderRadius: 1,
    },
    input: { ml: 1, flex: 1 },
    divider: { height: 28, m: 0.5, backgroundColor: '#404040' },
    iconButton: { p: '10px' },
};

// Define o tipo das props do componente
interface SearchBarProps {
  placeholder?: string; 
  onSearch?: (query: string) => void; 
}

const SearchBar: React.FC<SearchBarProps> = ({placeholder = 'Buscar cliente...',  onSearch}) => {
    const [query, setQuery] = useState<string>(''); // Estado para controlar o texto digitado

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
            sx={styles.form}
            onSubmit={handleSubmit} // Chama handleSubmit ao enviar o formulário
            >
            {/* Input search */}
            <InputBase
                sx={styles.input}
                placeholder={placeholder}
                value={query}
                onChange={handleChange} // Atualiza o texto no estado
                inputProps={{ 'aria-label': placeholder }}
            />

            <Divider sx={styles.divider} orientation="vertical" />
            
             {/* Botão para envio do forms */}
            <IconButton type="submit" sx={styles.iconButton} aria-label="Pesquisar">
                <SearchIcon color="primary" />
            </IconButton>
        </Box>
    );
};

export default SearchBar;
