import { useState, useEffect } from "react";
import { 
    Box, 
    Typography, 
    Button, 
    Collapse, 
    IconButton,
} from "@mui/material";
import { ExpandMore, ExpandLess } from "@mui/icons-material";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import Grid from "@mui/material/Grid2";
import CloseIcon from "@mui/icons-material/Close";


import { AddClientRequest, UpdateClientRequest } from "../../types/types";
import { SaveButton } from "../buttons";
import { useGetClientById, useAddClient, useUpdateClient } from "../../hooks/ClientHook";
import { usePopUp } from "../../context/PopUpContext";
import { 
    AddClientSchema, AddClientSchemaDefaultValues,
    UpdateClientSchema, UpdateClientSchemaDefaultValues
} from "./schemas";
import { TextInput, MaskedInput } from "../inputs";

interface ClientFormModalProps {
    isOpen: boolean;
    onClose: () => void;
    isEditing: boolean;
    clientId: number | null;
}

// Função auxiliar para lidar com campos aninhados
const getNestedFields = (dirtyFields: Record<string, any>, data: any) => {
    const result: any = {};
    
    Object.keys(dirtyFields).forEach(key => {
        if (typeof dirtyFields[key] === "object") {
            const nested = getNestedFields(dirtyFields[key], data[key]);
            if (Object.keys(nested).length > 0) {
                result[key] = nested;
            }
        } else if (dirtyFields[key]) {
            result[key] = data[key];
        }
    });
    
    return result;
};

const FormClient: React.FC<ClientFormModalProps> = ({onClose, isEditing, clientId }) => {

    const { 
        control, 
        reset, 
        handleSubmit, 
        formState: { errors, isDirty, dirtyFields } 
    } = useForm<AddClientRequest | UpdateClientRequest>({
        resolver: yupResolver(isEditing ? UpdateClientSchema : AddClientSchema),
        defaultValues: isEditing ? UpdateClientSchemaDefaultValues : AddClientSchemaDefaultValues,
        mode: "onChange" // Garantir atualização imediata do estado dirty
    });

    const { showMessage } = usePopUp();
    const [passportCollapse, setPassportCollapse] = useState(false);
    const [addressCollapse, setAddressCollapse] = useState(false); 

    // Hook para salvar e atualizar cliente 
    const { mutate: saveClient, isPending: isSaving } = useAddClient(onClose);
    const { mutate: updateClient, isPending: isUpdating } = useUpdateClient(onClose);

    // Busca os dados do cliente se estiver no modo de edição
    const { data: clientData, isError, error } = useGetClientById(clientId!);

    // Preenche o formulário com os dados do cliente quando eles são carregados
    useEffect(() => {
        if (isEditing && clientData) {
            reset({
                ...clientData,
            });
        }
    }, [clientData, isEditing, reset]);
    if (isError) {
        showMessage(error.message, "error");
    }

    // Função para enviar os dados do formulário
    const onSubmit = (data: AddClientRequest | UpdateClientRequest) => {
        if (isEditing && clientId) {
            if (!isDirty) {
                showMessage("Nenhuma alteração foi detectada.", "info");
                return;
            }

            const updatedClient = getNestedFields(dirtyFields, data);
            updateClient({ clientId, data: updatedClient });
        } else {
            saveClient(data as AddClientRequest);
        }
    };

    return (
        <Box
        component="form"
        onSubmit={handleSubmit(onSubmit)}
        sx={{
            display: "flex",
            flexDirection: "column",
            gap: "25px",
        }}>
            {/* Botão de fechar */}
            <IconButton
                aria-label="fechar"
                onClick={onClose}
                sx={{
                    position: "absolute",
                    top: 8,
                    right: 8,
                    color: "text.secondary",
                }}
            >
                <CloseIcon />
            </IconButton>

            <Typography variant="h1" textAlign="center" mb={"30px"}>
                {isEditing ? "Editar Cliente" : "Cadastrar Cliente"}
            </Typography>

            <Grid container spacing={2}>
                <Grid size={6}>
                    <TextInput
                        name="completeName"
                        control={control}
                        label="Nome Completo"
                        errors={errors}
                    />
                </Grid>

                <Grid size={6}>
                    <TextInput
                        name="email"
                        control={control}
                        label="Email"
                        errors={errors}
                    />
                </Grid>

                <Grid size={4}>
                    <MaskedInput
                        name="cpf"
                        control={control}
                        label="CPF"
                        mask="999.999.999-99"
                        errors={errors}
                        isEditing={isEditing}
                    />
                </Grid>

                <Grid size={4}>
                    <MaskedInput
                        name="birthDate"
                        control={control}
                        label="Data de Nascimento"
                        mask="99/99/9999"
                        errors={errors}
                    />
                </Grid>

                <Grid size={4}>
                    <MaskedInput
                        name="phone"
                        control={control}
                        label="Telefone"
                        mask="(99) 99999-9999"
                        errors={errors}
                    />
                </Grid>
            </Grid>

            {/* Seção de Passaporte */}
            <Box>
                <Button
                    fullWidth
                    variant="outlined"
                    onClick={() => setPassportCollapse(!passportCollapse)}
                    sx={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                        textTransform: "none",
                        padding: "10px",
                        ...(passportCollapse && {
                            border: "none"
                        }),
                    }}
                >
                    <Typography variant="h6">Passaporte</Typography>
                    {passportCollapse ? <ExpandLess /> : <ExpandMore />}
                </Button>
                <Collapse in={passportCollapse}>
                    <Grid container spacing={2}>
                        <Grid size={4}>
                            <TextInput
                                name="passport.number"
                                control={control}
                                label="Número do Passaporte"
                                errors={errors}
                            />
                        </Grid>

                        <Grid size={4}>
                            <MaskedInput
                                name="passport.emissionDate"
                                control={control}
                                label="Data de Emissão"
                                mask="99/99/9999"
                                errors={errors}
                            />
                        </Grid>

                        <Grid size={4}>
                            <MaskedInput
                                name="passport.expirationDate"
                                control={control}
                                label="Data de Validade"
                                mask="99/99/9999"
                                errors={errors}
                            />
                        </Grid>
                    </Grid>
                </Collapse>
            </Box>

            {/* Seção de Endereço */}
            <Box>
                <Button
                    fullWidth
                    variant="outlined"
                    onClick={() => setAddressCollapse(!addressCollapse)}
                    sx={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                        textTransform: "none",
                        padding: "10px",
                        ...(addressCollapse && {
                            border: "none"
                        }),
                    }}
                >
                    <Typography variant="h6">Endereço</Typography>
                    <IconButton>
                        {addressCollapse ? <ExpandLess /> : <ExpandMore />}
                    </IconButton>
                </Button>
                <Collapse in={addressCollapse}>
                    <Grid container spacing={2}>
                        <Grid size={4}>
                            <TextInput
                                name="address.country"
                                control={control}
                                label="País"
                                errors={errors}
                            />
                        </Grid>

                        <Grid size={5}>
                            <TextInput
                                name="address.state"
                                control={control}
                                label="Estado"
                                errors={errors}
                            />
                        </Grid>

                        <Grid size={3}>
                            <MaskedInput
                                name="address.zipCode"
                                control={control}
                                label="CEP"
                                mask="99999-999"
                                errors={errors}
                            />
                        </Grid>

                        <Grid size={6}>
                            <TextInput
                                name="address.city"
                                control={control}
                                label="Cidade"
                                errors={errors}
                            />
                        </Grid>

                        <Grid size={6}>
                            <TextInput
                                name="address.neighborhood"
                                control={control}
                                label="Bairro"
                                errors={errors}
                            />
                        </Grid>

                        <Grid size={8}>
                            <TextInput
                                name="address.street"
                                control={control}
                                label="Rua"
                                errors={errors}
                            />
                        </Grid>

                        <Grid size={4}>
                            <TextInput
                                name="address.residentialNumber"
                                control={control}
                                label="Número"
                                errors={errors}
                            />
                        </Grid>

                        <Grid size={12}>
                            <TextInput
                                name="address.complement"
                                control={control}
                                label="Complemento"
                                errors={errors}
                            />
                        </Grid>
                    </Grid>
                </Collapse>
            </Box>

            <SaveButton disabled={isSaving || isUpdating} />
        </Box>
    );
}

export default FormClient;