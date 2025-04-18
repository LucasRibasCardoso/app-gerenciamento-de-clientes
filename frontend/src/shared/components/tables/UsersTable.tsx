import React, { useState } from "react";
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Box,
} from "@mui/material";
import { UserResponse } from "../../types/types";

type Props = {
    users: UserResponse[];
    onSelectUser: (id: string | null) => void;
};

const UsersTable: React.FC<Props> = ({ users, onSelectUser }) => {
    const [selectedUser, setSelectedUser] = useState<string | null>(null);

    const handleDoubleClick = (id: string) => {
        const newSelectedUser = selectedUser === id ? null : id;
        setSelectedUser(newSelectedUser);
        onSelectUser(newSelectedUser);
    };

    return (
        <Box>
            <TableContainer
                component={Paper}
                sx={{
                    mt: "15px",
                }}
            >
                <Table size="small">
                    <TableHead sx={{ backgroundColor: "#616161", height: "45px" }}>
                        <TableRow>
                            <TableCell
                                sx={{
                                    color: "background.default",
                                    fontWeight: "bold",
                                    fontSize: "16px",
                                }}
                            >
                                ID
                            </TableCell>
                            <TableCell
                                sx={{
                                    color: "background.default",
                                    fontWeight: "bold",
                                    fontSize: "16px",
                                }}
                            >
                                Usuário
                            </TableCell>
                            <TableCell
                                sx={{
                                    color: "background.default",
                                    fontWeight: "bold",
                                    fontSize: "16px",
                                }}
                            >
                                Função
                            </TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {users.length === 0 ? (
                            <TableRow>
                                <TableCell
                                    colSpan={3}
                                    sx={{
                                        height: "45px",
                                        textAlign: "center",
                                        fontSize: "18px",
                                        fontWeight: "bold",
                                        color: "error.main",
                                    }}
                                >
                                    Nenhum usuário encontrado.
                                </TableCell>
                            </TableRow>
                        ) : (
                            users.map((user) => (
                                <TableRow
                                    key={user.id}
                                    onDoubleClick={() => handleDoubleClick(user.id)}
                                    sx={{
                                        cursor: "pointer",
                                        backgroundColor:
                                            selectedUser === user.id
                                                ? "rgba(76, 175, 79, 0.25)"
                                                : "inherit",
                                    }}
                                >
                                    <TableCell>{user.id}</TableCell>
                                    <TableCell>{user.username}</TableCell>
                                    <TableCell>{user.role}</TableCell>
                                </TableRow>
                            ))
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default UsersTable;
