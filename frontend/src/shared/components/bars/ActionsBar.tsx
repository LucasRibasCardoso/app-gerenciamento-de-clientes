import React from "react";
import { Box, Button } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import { ActionButton, ExportButton } from "../buttons";

interface ActionsBarProps {
	onDelete: () => void;
	onEdit: () => void;
	onCreate: () => void;
	isDisabled: boolean;
	entityName: string;
}

export const ActionsBar: React.FC<ActionsBarProps> = ({
	onDelete,
	onEdit,
	onCreate,
	isDisabled,
	entityName,
}) => {
	return (
		<Box sx={{ display: "flex", gap: "15px" }}>
			<ExportButton />

			<ActionButton
				icon={<DeleteIcon sx={{ mr: 1 }} />}
				text="DELETAR"
				hoverColor="#b00020"
				borderColor="primary.main"
				iconColor="primary.main"
				onClick={onDelete}
				disabled={isDisabled}
			/>

			<ActionButton
				icon={<EditIcon sx={{ mr: 1 }} />}
				text="EDITAR"
				hoverColor="#0A5C5A"
				borderColor="#404040"
				iconColor="primary"
				onClick={onEdit}
				disabled={isDisabled}
			/>

			<Button
				startIcon={<PersonAddIcon />}
				disableElevation
				variant="contained"
				onClick={onCreate}
			>
				{`CADASTRAR ${entityName.toUpperCase()}`}
			</Button>
		</Box>
	);
};
