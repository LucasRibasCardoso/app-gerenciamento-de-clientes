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
  Checkbox
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
      <Table>
        <TableHead>
          <TableRow>
            <TableCell />
            <TableCell />
            <TableCell>Nome</TableCell>
            <TableCell>CPF</TableCell>
            <TableCell>Telefone</TableCell>
            <TableCell>Email</TableCell>
            <TableCell>Data de Nascimento</TableCell>
          </TableRow>
        </TableHead>
        
        <TableBody>
          {clients.map((client) => (
            <React.Fragment key={client.id}>
              {/* Linha Principal */}
              <TableRow>
                <TableCell><Checkbox value={client.id}/></TableCell>
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
                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                  <Collapse in={openRows[client.id]} timeout="auto" unmountOnExit>
                    <Box margin={2}>
                      {/* Passaporte */}
                      <Typography variant="subtitle1" gutterBottom component="div">
                        Passaporte
                      </Typography>
                      <Table size="small" aria-label="passport">
                        <TableBody>
                          <TableRow>
                            <TableCell>Número</TableCell>
                            <TableCell>{client.passport?.number ?? "-"}</TableCell>
                          </TableRow>
                          <TableRow>
                            <TableCell>Data de Emissão</TableCell>
                            <TableCell>{client.passport?.emissionDate || "-"}</TableCell>
                          </TableRow>
                          <TableRow>
                            <TableCell>Data de Vencimento</TableCell>
                            <TableCell>{client.passport?.expirationDate || "-"}</TableCell>
                          </TableRow>
                        </TableBody>
                      </Table>

                      {/* Endereço */}
                      <Typography variant="subtitle1" gutterBottom component="div" style={{ marginTop: "16px" }}>
                        Endereço
                      </Typography>
                      <Table size="small" aria-label="address">
                        <TableBody>
                          <TableRow>
                            <TableCell>CEP</TableCell>
                            <TableCell>{client.address?.zipCode || "-"}</TableCell>
                          </TableRow>
                          <TableRow>
                            <TableCell>País</TableCell>
                            <TableCell>{client.address?.country || "-"}</TableCell>
                          </TableRow>
                          <TableRow>
                            <TableCell>Estado</TableCell>
                            <TableCell>{client.address?.state || "-"}</TableCell>
                          </TableRow>
                          <TableRow>
                            <TableCell>Cidade</TableCell>
                            <TableCell>{client.address?.city || "-"}</TableCell>
                          </TableRow>
                          <TableRow>
                            <TableCell>Bairro</TableCell>
                            <TableCell>{client.address?.neighborhood || "-"}</TableCell>
                          </TableRow>
                          <TableRow>
                            <TableCell>Rua</TableCell>
                            <TableCell>{client.address?.street || "-"}</TableCell>
                          </TableRow>
                          <TableRow>
                            <TableCell>Complemento</TableCell>
                            <TableCell>{client.address?.complement || "-"}</TableCell>
                          </TableRow>
                          <TableRow>
                            <TableCell>Número Residencial</TableCell>
                            <TableCell>{client.address?.residentialNumber || "-"}</TableCell>
                          </TableRow>
                        </TableBody>
                      </Table>
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