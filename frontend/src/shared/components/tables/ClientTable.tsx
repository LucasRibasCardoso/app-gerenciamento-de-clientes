import React, { useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Collapse,
  IconButton,
  Typography,
  Box,
  TablePagination
} from "@mui/material";
import { KeyboardArrowDown, KeyboardArrowUp } from "@mui/icons-material";
import { ClientResponse } from "../../types/types";

type Props = {
  pageSize: number;
  currentPage: number;
  totalElements: number;
  clients: ClientResponse[];
  onSelectClient: (id: string | null) => void;
  onPageChange: (newPage: number) => void;
  onPageSizeChange: (size: number) => void;
};

const ClientsTable: React.FC<Props> = ({
  pageSize,
  currentPage,
  totalElements: totalPages,
  clients,
  onSelectClient,
  onPageChange,
  onPageSizeChange
}) => {
  const [openRows, setOpenRows] = useState<Record<number, boolean>>({});
  const [selectedClient, setSelectedClient] = useState<string | null>(null);

  const toggleRow = (id: number) => {
    setOpenRows((prev) => ({ ...prev, [id]: !prev[id] }));
  };

  const handleDoubleClick = (id: number) => {
    const newSelectedClient = selectedClient === id.toString() ? null : id.toString();
    setSelectedClient(newSelectedClient);
    onSelectClient(newSelectedClient);
  };
  
  return (
    <Box>
      <TableContainer component={Paper} sx={{ mt: "15px" }}>
        <Table size="small">
          <TableHead sx={{ backgroundColor: "#616161", height: "45px"}}>
            <TableRow>
              <TableCell width={"75px"} />
              <TableCell sx={{ color: "background.default", fontWeight: "bold", fontSize: "16px"}}>Nome</TableCell>
              <TableCell sx={{ color: "background.default", fontWeight: "bold", fontSize: "16px"}} >CPF</TableCell>
              <TableCell sx={{ color: "background.default", fontWeight: "bold", fontSize: "16px"}} >Telefone</TableCell>
              <TableCell sx={{ color: "background.default", fontWeight: "bold", fontSize: "16px"}} >Email</TableCell>
              <TableCell sx={{ color: "background.default", fontWeight: "bold", fontSize: "16px"}} >Data de Nascimento</TableCell>
            </TableRow>
          </TableHead>
          
          <TableBody>
            {clients.length === 0 ? (
              <TableRow>
                <TableCell 
                  colSpan={7} 
                  sx={{
                    height: "45px",
                    textAlign: "center",
                    fontSize: "18px",
                    fontWeight: "bold",
                    color: "error.main"
                }}>
                  Nenhum cliente encontrado.
                </TableCell>
              </TableRow>
            ) : (
              clients.map((client) => (
                <React.Fragment key={client.id}>
                  {/* Linha Principal */}
                  <TableRow
                    key={client.id}
                    onDoubleClick={() => handleDoubleClick(client.id)}
                    sx={{
                      cursor: "pointer",
                      backgroundColor: selectedClient === client.id.toString() ? "rgba(76, 175, 79, 0.25)" : "inherit",
                    }}
                  >
                    <TableCell width={"75px"}>
                      <IconButton
                        onClick={(e) => {
                          e.stopPropagation();
                          toggleRow(client.id);
                        }}
                        aria-label="expand row"
                        size="small"
                      >
                        {openRows[client.id] ? <KeyboardArrowUp /> : <KeyboardArrowDown />}
                      </IconButton>
                    </TableCell>
                    <TableCell>{client.completeName}</TableCell>
                    <TableCell>{client.cpf || "-"}</TableCell>
                    <TableCell>{client.phone || "-"}</TableCell>
                    <TableCell>{client.email || "-"}</TableCell>
                    <TableCell>{client.birthDate || "-"}</TableCell>
                  </TableRow>

                  {/* Linha Expandida */}
                  <TableRow >
                    <TableCell sx={{ paddingBottom: 0, paddingTop: 0 }} colSpan={7}>
                      <Collapse in={openRows[client.id]} timeout="auto" unmountOnExit>
                        <Box margin={1} display={"flex"} gap={"150px"}>
                          {/* Endereço */}
                          <Box>
                            <Table size="small" aria-label="address">
                              <Typography variant="subtitle1">Endereço</Typography>
                              <TableBody>
                                <TableRow>
                                  <TableCell>CEP</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.address?.zipCode || "-"}</TableCell>
                                </TableRow>
                                <TableRow>
                                  <TableCell>País</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.address?.country || "-"}</TableCell>
                                </TableRow>
                                <TableRow>
                                  <TableCell>Estado</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.address?.state || "-"}</TableCell>
                                </TableRow>
                                <TableRow>
                                  <TableCell>Cidade</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.address?.city || "-"}</TableCell>
                                </TableRow>
                                <TableRow>
                                  <TableCell>Bairro</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.address?.neighborhood || "-"}</TableCell>
                                </TableRow>
                                <TableRow>
                                  <TableCell>Rua</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.address?.street || "-"}</TableCell>
                                </TableRow>
                                <TableRow>
                                  <TableCell>Complemento</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.address?.complement || "-"}</TableCell>
                                </TableRow>
                                <TableRow>
                                  <TableCell>Número Residencial</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.address?.residentialNumber || "-"}</TableCell>
                                </TableRow>
                              </TableBody>
                            </Table>
                          </Box>

                          {/* Passaporte */}
                          <Box>
                            <Table size="small" aria-label="passport">
                              <Typography variant="subtitle1">Passaporte</Typography>
                              <TableBody>
                                <TableRow>
                                  <TableCell>Número</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.passport?.number ?? "-"}</TableCell>
                                </TableRow>
                                <TableRow>
                                  <TableCell>Data de Emissão</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.passport?.emissionDate || "-"}</TableCell>
                                </TableRow>
                                <TableRow>
                                  <TableCell>Data de Vencimento</TableCell>
                                  <TableCell sx={{ textAlign: "end" }}>{client.passport?.expirationDate || "-"}</TableCell>
                                </TableRow>
                              </TableBody>
                            </Table>
                          </Box>
                        </Box>
                      </Collapse>
                    </TableCell>
                  </TableRow>
                </React.Fragment>
              ))
            )}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Paginação */}
      <Box sx={{ display: "flex", justifyContent: "center", margin: 2 }}>
        <TablePagination
          component="div"
          count={totalPages}
          rowsPerPage={pageSize}
          page={currentPage}
          onPageChange={(_, newPage) => onPageChange(newPage)}
          onRowsPerPageChange={(event) => onPageSizeChange(parseInt(event.target.value, 10))}
          rowsPerPageOptions={[5, 10, 25, 50]}
          labelRowsPerPage="Clientes por página"
        />
      </Box>
    </Box>

  );
};

export default ClientsTable;