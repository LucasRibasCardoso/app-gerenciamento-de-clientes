import React from "react";
import { Box, Button } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import PersonAddIcon from "@mui/icons-material/PersonAdd";

import ActionButton from "../action-button/ActionButton";
import SearchBar from "../search-bar/SearchBar";
import SelectButton from "../action-button/SelectButton"; 

interface FunctionalityBarProps {
  onSearch: (query: string) => void;
  orderBy: string;
  onOrderByChange: (value: string) => void;
  orderDirection: string;
  onOrderDirectionChange: (value: "asc" | "desc") => void;
  selectedClient: string | null;
  onDeleteClient: () => void;
  onEditClient: () => void;
  onCreateClient: () => void;
}

const FunctionalityBar: React.FC<FunctionalityBarProps> = ({
  onSearch,
  orderBy,
  onOrderByChange,
  orderDirection,
  onOrderDirectionChange,
  selectedClient,
  onDeleteClient,
  onEditClient,
  onCreateClient,
}) => {
  return (
    <Box sx={{ display: "flex", justifyContent: "space-between", margin: "15px", height: "45px" }}>
      <Box sx={{ display: "flex", gap: "15px" }}>
        {/* Barra de pesquisa */}
        <SearchBar onSearch={onSearch} />

        {/* Seleção de campo de ordenação */}
        <SelectButton
          label="Ordenar"
          value={orderBy}
          options={[
            { value: "id", label: "Recente Adicionado" },
            { value: "completeName", label: "Nome" },
          ]}
          onChange={onOrderByChange}
        />

        {/* Seleção de direção da ordenação */}
        <SelectButton
          label="Direção"
          value={orderDirection}
          options={[
            { value: "asc", label: "Crescente" },
            { value: "desc", label: "Decrescente" },
          ]}
          onChange={(value) => onOrderDirectionChange(value as "asc" | "desc")}
        />
      </Box>

      <Box sx={{ display: "flex", gap: "15px" }}>
        <ActionButton
          icon={<DeleteIcon sx={{ mr: 1 }} />}
          text="DELETAR"
          hoverColor="#b00020"
          borderColor="primary.main"
          iconColor="primary.main"
          onClick={onDeleteClient}
          disabled={!selectedClient}
        />

        <ActionButton
          icon={<EditIcon sx={{ mr: 1 }} />}
          text="EDITAR"
          hoverColor="#0A5C5A"
          borderColor="#404040"
          iconColor="primary"
          onClick={onEditClient}
          disabled={!selectedClient}
        />

        <Button
          startIcon={<PersonAddIcon />}
          disableElevation
          variant="contained"
          onClick={onCreateClient}
        >
          CADASTRAR CLIENTE
        </Button>
      </Box>
    </Box>
  );
};

export default FunctionalityBar;