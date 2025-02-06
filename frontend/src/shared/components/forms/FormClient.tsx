import { useState, useEffect } from "react";
import { 
    Box, 
    Typography, 
    TextField, 
    Button, 
    Collapse, 
    IconButton,
} from "@mui/material";
import { ExpandMore, ExpandLess } from "@mui/icons-material";
import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import Grid from "@mui/material/Grid2";
import InputMask from "react-input-mask";
import CloseIcon from "@mui/icons-material/Close";


import { AddClientRequest, UpdateClientRequest } from "../../types/types";
import { SaveButton } from "../buttons";
import { useGetClientById, useAddClient, useUpdateClient } from "../../hooks/ClientHook";
import { usePopUp } from "../../context/PopUpContext";
import { 
    AddClientSchema, AddClientSchemaDefaultValues,
    UpdateClientSchema, UpdateClientSchemaDefaultValues
} from "./schemas";

interface ClientFormModalProps {
    isOpen: boolean;
    onClose: () => void;
    isEditing: boolean;
    clientId: number | null;
}

const FormClient: React.FC<ClientFormModalProps> = ({onClose, isEditing, clientId }) => {

    const { 
        control, 
        reset, 
        handleSubmit, 
        getValues,
        formState: { errors }
    } = useForm<AddClientRequest | UpdateClientRequest>({
        resolver: yupResolver(
            isEditing 
                ? UpdateClientSchema 
                : AddClientSchema
        ),
        defaultValues: isEditing ? UpdateClientSchemaDefaultValues : AddClientSchemaDefaultValues
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
            const currentValues = getValues();
            
            const dataUpdate = {
                completeName: currentValues.completeName != clientData?.completeName ? currentValues.completeName : null,
                cpf: null,
                birthDate: currentValues.birthDate != clientData?.birthDate ? currentValues.birthDate : null,
                phone: currentValues.phone != clientData?.phone ? currentValues.phone : null,
                email: currentValues.email !== clientData?.email ? currentValues.email : null,

                passport: {
                    number: currentValues.passport.number !== clientData?.passport?.number ? currentValues.passport.number : null,
                    emissionDate: currentValues.passport.emissionDate !== clientData?.passport?.emissionDate ? currentValues.passport.emissionDate : null,
                    expirationDate: currentValues.passport.expirationDate !== clientData?.passport?.expirationDate ? currentValues.passport.expirationDate : null,
                },

                address: {
                    zipCode: currentValues.address.zipCode !== clientData?.address?.zipCode ? currentValues.address.zipCode : null,
                    country: currentValues.address.country !== clientData?.address?.country ? currentValues.address.country : null,
                    state: currentValues.address.state !== clientData?.address?.state ? currentValues.address.state : null,
                    city: currentValues.address.city !== clientData?.address?.city ? currentValues.address.city : null,
                    neighborhood: currentValues.address.neighborhood !== clientData?.address?.neighborhood ? currentValues.address.neighborhood : null,
                    street: currentValues.address.street !== clientData?.address?.street ? currentValues.address.street : null,
                    complement: currentValues.address.complement !== clientData?.address?.complement ? currentValues.address.complement : null,
                    residentialNumber: currentValues.address.residentialNumber !== clientData?.address?.residentialNumber ? currentValues.address.residentialNumber : null,
                }
            };

            updateClient({ clientId, data: dataUpdate as UpdateClientRequest});
        } 
        else {
            saveClient(data as AddClientRequest);
        }
    };

    // Funções para abrir as seções de Passaporte e Endereço
    const togglePassportCollapse = () => {
        setPassportCollapse(!passportCollapse);
    };

    const toggleAddressCollapse = () => {
        setAddressCollapse(!addressCollapse);
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
                        <Controller
                            name="completeName"
                            control={control}
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    label="Nome Completo"
                                    variant="outlined"
                                    fullWidth
                                    error={!!errors.completeName}
                                    helperText={errors.completeName?.message}
                                />
                            )}
                        />
                    </Grid>

                    <Grid size={6}>
                        <Controller
                            name="email"
                            control={control}
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    label="Email"
                                    variant="outlined"
                                    fullWidth
                                    error={!!errors.email}
                                    helperText={errors.email?.message}
                                />
                            )}
                        />
                    </Grid>

                    <Grid size={4}>
                        <Controller
                            name="cpf"
                            control={control}
                            render={({ field }) => (
                                <InputMask
                                    mask="999.999.999-99"
                                    value={field.value || ""}
                                    onChange={field.onChange}
                                    disabled={isEditing}
                                >
                                    {(inputProps: any) => (
                                        <TextField
                                            {...inputProps}
                                            label="CPF"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.cpf}
                                            helperText={errors.cpf?.message}
                                        />
                                    )}
                                </InputMask>
                            )}
                        />
                    </Grid>

                    <Grid size={4}>
                        <Controller
                            name="birthDate"
                            control={control}
                            render={({ field }) => (
                                <InputMask
                                    mask="99/99/9999"
                                    value={field.value || ""}
                                    onChange={field.onChange}
                                >
                                    {(inputProps: any) => (
                                        <TextField
                                            {...inputProps}
                                            label="Data de Nascimento"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.birthDate}
                                            helperText={errors.birthDate?.message}
                                        />
                                    )}
                                </InputMask>
                            )}
                        />
                    </Grid>

                    <Grid size={4}>
                        <Controller
                            name="phone"
                            control={control}
                            render={({ field }) => (
                                <InputMask
                                    mask="(99) 99999-9999"
                                    value={field.value || ""}
                                    onChange={field.onChange}
                                >
                                    {(inputProps: any) => (
                                        <TextField
                                            {...inputProps}
                                            label="Telefone"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.phone}
                                            helperText={errors.phone?.message}
                                        />
                                    )}
                                </InputMask>
                            )}
                        />
                    </Grid>
            </Grid>

            {/* Seção de Passaporte */}
            <Box>
                <Button
                    fullWidth
                    variant="outlined"
                    onClick={togglePassportCollapse}
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
                                <Controller
                                    name="passport.number"
                                    control={control}
                                    render={({ field }) => (
                                        <TextField
                                            {...field}
                                            label="Número do Passaporte"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.passport?.number}
                                            helperText={errors.passport?.number?.message}
                                        />
                                    )}
                                />
                            </Grid>

                            <Grid size={4}> 
                                <Controller
                                    name="passport.emissionDate"
                                    control={control}
                                    render={({ field }) => (
                                        <InputMask
                                            mask="99/99/9999"
                                            value={field.value || ""}
                                            onChange={field.onChange}
                                        >
                                            {(inputProps: any) => (
                                                <TextField
                                                    {...inputProps}
                                                    label="Data de Emissão"
                                                    variant="outlined"
                                                    fullWidth
                                                    error={!!errors.passport?.emissionDate}
                                                    helperText={errors.passport?.emissionDate?.message}
                                                />
                                            )}
                                        </InputMask>
                                    )}
                                />
                            </Grid>

                            <Grid size={4}>
                                <Controller
                                    name="passport.expirationDate"
                                    control={control}
                                    render={({ field }) => (
                                        <InputMask
                                            mask="99/99/9999"
                                            value={field.value || ""}
                                            onChange={field.onChange}
                                        >
                                            {(inputProps: any) => (
                                                <TextField
                                                    {...inputProps}
                                                    label="Data de Validade"
                                                    variant="outlined"
                                                    fullWidth
                                                    error={!!errors.passport?.expirationDate}
                                                    helperText={errors.passport?.expirationDate?.message}
                                                />
                                            )}
                                        </InputMask>
                                    )}
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
                    onClick={toggleAddressCollapse}
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
                    <IconButton onClick={toggleAddressCollapse}>
                        {addressCollapse ? <ExpandLess /> : <ExpandMore />}
                    </IconButton>
                </Button>
                <Collapse in={addressCollapse}>
                        <Grid container spacing={2}>
                            
                            <Grid size={4}>
                                <Controller
                                    name="address.country"
                                    control={control}
                                    render={({ field }) => (
                                        <TextField
                                            {...field}
                                            label="País"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.address?.country}
                                            helperText={errors.address?.country?.message}
                                        />
                                    )}
                                />
                            </Grid>

                            <Grid size={5}>
                                <Controller
                                    name="address.state"
                                    control={control}
                                    render={({ field }) => (
                                        <TextField
                                            {...field}
                                            label="Estado"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.address?.state}
                                            helperText={errors.address?.state?.message}
                                        />
                                    )}
                                />
                            </Grid>

                            <Grid size={3}>
                                <Controller
                                    name="address.zipCode"
                                    control={control}
                                    render={({ field }) => (
                                        <InputMask
                                            mask="99999-999"
                                            value={field.value || ""}
                                            onChange={field.onChange}
                                        >
                                            {(inputProps: any) => (
                                                <TextField
                                                    {...inputProps}
                                                    label="CEP"
                                                    variant="outlined"
                                                    fullWidth
                                                    error={!!errors.address?.zipCode}
                                                    helperText={errors.address?.zipCode?.message}
                                                />
                                            )}
                                        </InputMask>
                                    )}
                                />
                            </Grid>

                            <Grid size={6}>
                                <Controller
                                    name="address.city"
                                    control={control}
                                    render={({ field }) => (
                                        <TextField
                                            {...field}
                                            label="Cidade"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.address?.city}
                                            helperText={errors.address?.city?.message}
                                        />
                                    )}
                                />
                            </Grid>

                            <Grid size={6}>
                                <Controller
                                    name="address.neighborhood"
                                    control={control}
                                    render={({ field }) => (
                                        <TextField
                                            {...field}
                                            label="Bairro"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.address?.neighborhood}
                                            helperText={errors.address?.neighborhood?.message}
                                        />
                                    )}
                                />
                            </Grid>

                            <Grid size={8}>
                                <Controller
                                    name="address.street"
                                    control={control}
                                    render={({ field }) => (
                                        <TextField
                                            {...field}
                                            label="Rua"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.address?.street}
                                            helperText={errors.address?.street?.message}
                                        />
                                    )}
                                />
                            </Grid>

                            <Grid size={4}>
                                <Controller
                                    name="address.residentialNumber"
                                    control={control}
                                    render={({ field }) => (
                                        <TextField
                                            {...field}
                                            label="Número"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.address?.residentialNumber}
                                            helperText={errors.address?.residentialNumber?.message}
                                        />
                                    )}
                                />
                            </Grid>

                            <Grid size={12}>
                                <Controller
                                    name="address.complement"
                                    control={control}
                                    render={({ field }) => (
                                        <TextField
                                            {...field}
                                            label="Complemento"
                                            variant="outlined"
                                            fullWidth
                                            error={!!errors.address?.complement}
                                            helperText={errors.address?.complement?.message}
                                        />
                                    )}
                                />
                            </Grid>
                        </Grid>
                </Collapse>
            </Box>

            <SaveButton disabled={isSaving || isUpdating}/>
        </Box>
    );
}

export default FormClient;