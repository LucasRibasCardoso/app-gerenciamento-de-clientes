import React from "react";
import { Box } from "@mui/material";
import { SelectButton } from "../buttons";
import { SearchBar } from "./";
import { ActionsBar } from "./ActionsBar";
import { useAuth } from "../../hooks/AuthHook";

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
    const { isManager, isAdmin } = useAuth();
    const hasPermission = isManager || isAdmin;

    return (
        <Box
            sx={{
                display: "flex",
                justifyContent: "space-between",
                height: "45px",
                gap: "15px",
            }}
        >
            <Box sx={{ display: "flex", gap: "15px" }}>
                <SearchBar onSearch={onSearch} />

                <SelectButton
                    label="Ordenar"
                    value={orderBy}
                    options={[
                        { value: "id", label: "Recente Adicionado" },
                        { value: "completeName", label: "Nome" },
                    ]}
                    onChange={onOrderByChange}
                />

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

            {hasPermission && (
                <ActionsBar
                    onDelete={onDeleteClient}
                    onEdit={onEditClient}
                    onCreate={onCreateClient}
                    isDisabled={!selectedClient}
                    entityName="CLIENTE"
                />
            )}
        </Box>
    );
};

export default FunctionalityBar;
