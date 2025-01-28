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
} from "@mui/material";
import { KeyboardArrowDown, KeyboardArrowUp } from "@mui/icons-material";
import { ListOfClient } from "../../types/types";

type Props = {
  clients: ListOfClient;
};

const ClientsTable: React.FC<Props> = ({ clients }) => {
  const [openRows, setOpenRows] = useState<Record<number, boolean>>({});

  const toggleRow = (id: number) => {
    setOpenRows((prev) => ({ ...prev, [id]: !prev[id] }));
  };

  return (
    <TableContainer component={Paper}>
      <Table size="small">
        <TableHead sx={{ backgroundColor: "#616161" }}>
          <TableRow>
            <TableCell />
            <TableCell sx={{ color: "background.default", fontWeight: "bold"}}>Nome</TableCell>
            <TableCell sx={{ color: "background.default", fontWeight: "bold"}} >CPF</TableCell>
            <TableCell sx={{ color: "background.default", fontWeight: "bold"}} >Telefone</TableCell>
            <TableCell sx={{ color: "background.default", fontWeight: "bold"}} >Email</TableCell>
            <TableCell sx={{ color: "background.default", fontWeight: "bold"}} >Data de Nascimento</TableCell>
          </TableRow>
        </TableHead>
        
        <TableBody>
          {clients.map((client) => (
            <React.Fragment key={client.id}>
              {/* Linha Principal */}
              <TableRow 
                sx={{
                  backgroundColor: openRows[client.id] ? "#BFBFBF" : "inherit",
                  ":hover": { backgroundColor: "#BFBFBF" },
                }}
              >
                <TableCell>
                  <IconButton
                    onClick={() => toggleRow(client.id)}
                    aria-label="expand row"
                    size="small"
                  >
                    {openRows[client.id] ? (
                      <KeyboardArrowUp />
                    ) : (
                      <KeyboardArrowDown />
                    )}
                  </IconButton>
                </TableCell>
                <TableCell>{client.completeName}</TableCell>
                <TableCell>{client.cpf || "-"}</TableCell>
                <TableCell>{client.phone || "-"}</TableCell>
                <TableCell>{client.email || "-"}</TableCell>
                <TableCell>{client.birthDate || "-"}</TableCell>
              </TableRow>

              {/* Linha Expandida */}
              <TableRow>
                <TableCell sx={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                  <Collapse in={openRows[client.id]} timeout="auto" unmountOnExit>
                    <Box margin={1} display={"flex"} gap={"150px"}>
                      {/* Endereço */}
                      <Box>
                        <Table size="small" aria-label="address">
                          <Typography variant="subtitle1" component="div">
                            Endereço
                          </Typography>
                          <TableBody>
                            <TableRow>
                              <TableCell>CEP</TableCell>
                              <TableCell sx={{ textAlign: "end" }} >{client.address?.zipCode || "-"}</TableCell>
                            </TableRow>
                            <TableRow>
                              <TableCell>País</TableCell>
                              <TableCell sx={{ textAlign: "end" }} >{client.address?.country || "-"}</TableCell>
                            </TableRow>
                            <TableRow>
                              <TableCell>Estado</TableCell>
                              <TableCell sx={{ textAlign: "end" }} >{client.address?.state || "-"}</TableCell>
                            </TableRow>
                            <TableRow>
                              <TableCell>Cidade</TableCell>
                              <TableCell sx={{ textAlign: "end" }} >{client.address?.city || "-"}</TableCell>
                            </TableRow>
                            <TableRow>
                              <TableCell>Bairro</TableCell>
                              <TableCell sx={{ textAlign: "end" }} >{client.address?.neighborhood || "-"}</TableCell>
                            </TableRow>
                            <TableRow>
                              <TableCell>Rua</TableCell>
                              <TableCell sx={{ textAlign: "end" }} >{client.address?.street || "-"}</TableCell>
                            </TableRow>
                            <TableRow>
                              <TableCell>Complemento</TableCell>
                              <TableCell sx={{ textAlign: "end" }} >{client.address?.complement || "-"}</TableCell>
                            </TableRow>
                            <TableRow>
                              <TableCell>Número Residencial</TableCell>
                              <TableCell sx={{ textAlign: "end" }}> {client.address?.residentialNumber || "-"}</TableCell>
                            </TableRow>
                          </TableBody>
                        </Table>
                      </Box>
                      
                      {/* Passaporte */}
                      <Box>
                        <Table size="small" aria-label="passport">
                          <Typography variant="subtitle1" component="div">
                            Passaporte
                          </Typography>
                          <TableBody>
                            <TableRow>
                              <TableCell>Número</TableCell>
                              <TableCell sx={{ textAlign: "end" }} >{client.passport?.number ?? "-"}</TableCell>
                            </TableRow>
                            <TableRow>
                              <TableCell>Data de Emissão</TableCell>
                              <TableCell sx={{ textAlign: "end" }} >{client.passport?.emissionDate || "-"}</TableCell>
                            </TableRow>
                            <TableRow>
                              <TableCell>Data de Vencimento</TableCell>
                              <TableCell sx={{ textAlign: "end" }} >{client.passport?.expirationDate || "-"}</TableCell>
                            </TableRow>
                          </TableBody>
                        </Table>
                      </Box>
                    </Box>
                  </Collapse>
                </TableCell>
              </TableRow>
            </React.Fragment>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ClientsTable;