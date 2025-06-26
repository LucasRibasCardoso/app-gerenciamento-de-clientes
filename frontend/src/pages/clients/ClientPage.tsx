import { useState } from "react";
import { Box, Backdrop, CircularProgress, Modal } from "@mui/material";

import {
	useGetAllClients,
	useDeleteClient,
} from "../../shared/hooks/ClientHook";
import { ClientsTable } from "../../shared/components/tables";
import { FunctionalityBar } from "../../shared/components/bars";
import Layout from "../../shared/layouts/Layout";
import { FormClient } from "../../shared/components/forms";

export default function Clients() {
	document.title = "Clientes - Client Management";

	const [searchQuery, setSearchQuery] = useState<string>("");
	const [orderBy, setOrderBy] = useState<string>("");
	const [orderDirection, setOrderDirection] = useState<string>("");
	const [selectedClient, setSelectedClient] = useState<string | null>(null);
	const [page, setPage] = useState<number>(0);
	const [size, setSize] = useState<number>(10);

	const [isModalOpen, setIsModalOpen] = useState(false);
	const [isEditing, setIsEditing] = useState(false);
	const [editingClientId, setEditingClientId] = useState<number | null>(null);

	// Hook para busca de clientes e paginação
	const { data: clients, isLoading } = useGetAllClients({
		searchQuery,
		orderBy,
		orderDirection,
		page,
		size,
	});

	// Hook para deletar cliente
	const { mutate: deleteClient, isPending: isDeleting } = useDeleteClient();

	// Função para deletar cliente
	const handleDeleteClient = () => {
		if (selectedClient) {
			deleteClient(Number(selectedClient));
			setSelectedClient(null);
		}
	};

	// Função para abrir o modal de edição
	const handleEditClient = () => {
		if (selectedClient) {
			setIsEditing(true);
			setEditingClientId(Number(selectedClient));
			setIsModalOpen(true);
		}
	};

	// Função para abrir o modal de criação
	const handleCreateClient = () => {
		setIsEditing(false);
		setEditingClientId(null);
		setIsModalOpen(true);
	};

	// Função para fechar o modal
	const handleCloseModal = () => {
		setIsModalOpen(false);
		setIsEditing(false);
		setEditingClientId(null);
	};

	return (
		<Layout>
			{/* Backdrop para loading */}
			<Backdrop
				sx={(theme) => ({ color: "#fff", zIndex: theme.zIndex.drawer + 1 })}
				open={isDeleting || isLoading}
			>
				<CircularProgress color="inherit" />
			</Backdrop>

			{/* Barra de funcionalidade */}
			<FunctionalityBar
				onSearch={(query) => setSearchQuery(query)}
				orderBy={orderBy}
				onOrderByChange={(value) => setOrderBy(value)}
				orderDirection={orderDirection}
				onOrderDirectionChange={(value) =>
					setOrderDirection(value as "asc" | "desc")
				}
				selectedClient={selectedClient}
				onDeleteClient={handleDeleteClient}
				onEditClient={handleEditClient}
				onCreateClient={handleCreateClient}
			/>

			{/* Tabela de clientes */}
			<ClientsTable
				pageSize={size}
				currentPage={page}
				totalElements={clients?.totalElements || 0}
				clients={clients?.data || []}
				onSelectClient={(id) =>
					setSelectedClient(id === selectedClient ? null : id)
				}
				onPageChange={(newPage) => setPage(newPage)}
				onPageSizeChange={(newSize) => {
					setSize(newSize);
					setPage(0);
				}}
			/>

			{/* Modal de edição e cadastro */}
			<Modal
				open={isModalOpen}
				onClose={handleCloseModal}
				aria-labelledby="client-form-modal"
				aria-describedby="client-form-modal-description"
			>
				<Box
					sx={{
						position: "absolute",
						top: "50%",
						left: "50%",
						transform: "translate(-50%, -50%)",
						width: "90%",
						maxWidth: "650px",
						bgcolor: "background.paper",
						boxShadow: 24,
						p: 4,
						borderRadius: 2,
					}}
				>
					<FormClient
						isOpen={isModalOpen}
						onClose={handleCloseModal}
						isEditing={isEditing}
						clientId={editingClientId}
					/>
				</Box>
			</Modal>
		</Layout>
	);
}
