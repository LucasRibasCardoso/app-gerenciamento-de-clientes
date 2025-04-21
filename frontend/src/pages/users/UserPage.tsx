import { useState } from "react";
import { Box, Backdrop, CircularProgress, Modal } from "@mui/material";

import Layout from "../../shared/layouts/Layout";
import { ActionsBar } from "../../shared/components/bars/ActionsBar";
import { useGetAllUsers, useDeleteUser } from "../../shared/hooks/UserHook";
import { UsersTable } from "../../shared/components/tables";
import { FormUser } from "../../shared/components/forms";

export default function UserPage() {
    document.title = "Gerenciamento de Usuários";

    const [selectedUserId, setSelectedUserId] = useState<string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isEditing, setIsEditing] = useState(false);

    // Hook para busca de usuários
    const { data: users, isLoading } = useGetAllUsers();

    // Hook para deletar usuário
    const { mutate: deleteUser, isPending: isDeleting } = useDeleteUser();

    // Função para deletar usuário
    const handleDeleteUser = () => {
        if (selectedUserId) {
            deleteUser(selectedUserId);
            setSelectedUserId(null);
        }
    };

    // Função para abrir o modal de edição
    const handleEditUser = () => {
        if (selectedUserId) {
            setIsEditing(true);
            setIsModalOpen(true);
        }
    };

    // Função para abrir o modal de criação
    const handleCreateUser = () => {
        setSelectedUserId(null);
        setIsEditing(false);
        setIsModalOpen(true);
    };

    // Função para fechar o modal
    const handleCloseModal = () => {
        setIsModalOpen(false);
        setIsEditing(false);
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
            <ActionsBar
                onDelete={handleDeleteUser}
                onEdit={handleEditUser}
                onCreate={handleCreateUser}
                isDisabled={!selectedUserId}
                entityName="USUÁRIO"
            />

            {/* Tabela de usuários */}
            <UsersTable
                users={users || []}
                onSelectUser={setSelectedUserId}
            />

            {/* Modal de edição e cadastro */}
            <Modal
                open={isModalOpen}
                onClose={handleCloseModal}
                aria-labelledby="user-form-modal"
                aria-describedby="user-form-modal-description"
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
                    <FormUser
                        isOpen={isModalOpen}
                        onClose={handleCloseModal}
                        isEditing={isEditing}
                        userId={selectedUserId}
                    />
                </Box>
            </Modal>
        </Layout>
    );
}
