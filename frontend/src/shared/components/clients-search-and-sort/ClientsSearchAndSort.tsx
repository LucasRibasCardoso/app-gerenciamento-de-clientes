import React, { useState, useEffect } from "react";
import { Box } from "@mui/material";

import SearchBar from "../search-bar/SearchBar";
import SelectButton from "../select-button/SelectButton";

interface ClientsSearchAndSortProps {
  onFiltersChange?: (filters: { searchQuery: string; orderBy: string; orderDirection: string }) => void;
}

const ClientsSearchAndSort: React.FC<ClientsSearchAndSortProps> = ({ onFiltersChange }) => {
  const [searchQuery, setSearchQuery] = useState<string>("");
  const [orderBy, setOrderBy] = useState<string>("");
  const [orderDirection, setOrderDirection] = useState<string>("");

  const buildUrl = () => {
    const baseUrl = "https://api.seusistema.com/clients";
    const params = new URLSearchParams();   
    
    if (searchQuery) params.append("search", searchQuery);
    if (orderBy) params.append("orderBy", orderBy);
    if (orderDirection) params.append("sortOrder", orderDirection); 
    
    return `${baseUrl}?${params.toString()}`;
  };

  useEffect(() => {
    const url = buildUrl();
    console.log("URL da API:", url);

    // Propaga os filtros para o componente pai, caso a prop onFiltersChange seja fornecida
    if (onFiltersChange) {
      onFiltersChange({ searchQuery, orderBy, orderDirection });
    }

    // Realizar a chamada à API, se necessário
    // fetch(url).then(response => response.json()).then(data => console.log(data));
  }, [searchQuery, orderBy, orderDirection, onFiltersChange]);

  return (
    <Box sx={{display: "flex", gap: "15px", height: "45px"}}>
      {/* Barra de pesquisa */}
      <SearchBar onSearch={(query) => setSearchQuery(query)} />

      {/* Seleção de campo de ordenação */}
      <SelectButton
        label="Ordenar"
        value={orderBy}
        options={[
          { value: "id", label: "ID" },
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
  );
};

export default ClientsSearchAndSort;