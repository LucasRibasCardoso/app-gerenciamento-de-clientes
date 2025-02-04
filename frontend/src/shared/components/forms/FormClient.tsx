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
import * as yup from "yup";
import Grid from "@mui/material/Grid2";
import InputMask from "react-input-mask";
import CloseIcon from '@mui/icons-material/Close';


import { ClientRequest, ClientRequestUpdate } from "../../types/types";
import { SaveButton } from "../buttons";
import { useSaveClient, useGetClientById } from "../../hooks/ClientHook";


const clientSchema = yup.object().shape({
    completeName: yup.string()
        .required("O nome completo é obrigatório")
        .max(100, "O nome completo deve ter no máximo 100 caracteres"),

    cpf: yup.string()
        .required("O CPF é obrigatório")
        .max(14, "O CPF deve ter no máximo 14 caracteres")
        .matches(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/, "CPF inválido"),

    birthDate: yup.string()
        .required("A data de nascimento é obrigatória")
        .matches(/^\d{2}\/\d{2}\/\d{4}$/, "Data de nascimento deve estar no formato dd/MM/yyyy")
        .test("is-valid-date", "Data de nascimento inválida", (value) => {
            if (!value) return true; // Permite valores nulos
            const [day, month, year] = value.split("/");
            const date = new Date(`${year}-${month}-${day}`);
            return !isNaN(date.getTime()); // Verifica se a data é válida
        }),

    phone: yup.string()
        .nullable()
        .transform((value) => value || null) // Converte strings vazias para null
        .matches(/\(\d{2}\) \d{4,5}-\d{4}/, "Formato inválido")
        .max(15, "O telefone deve ter no máximo 15 caracteres"),

    email: yup.string()
        .nullable()
        .transform((value) => value || null) // Converte strings vazias para null
        .email("E-mail inválido")
        .max(100, "O e-mail deve ter no máximo 100 caracteres"),

    passport: yup.object().shape({
        number: yup.string()
            .nullable()
            .transform((value) => value || null),
        emissionDate: yup.string()
            .nullable()
            .transform((value) => value || null)
            .test("is-valid-date", "Data de emissão inválida", (value) => {
                if (!value) return true; // Permite valores nulos
                const [day, month, year] = value.split("/");
                const date = new Date(`${year}-${month}-${day}`);
                return !isNaN(date.getTime()); // Verifica se a data é válida
            }),
        expirationDate: yup.string()
            .nullable()
            .transform((value) => value || null)
            .test("is-valid-date", "Data de validade inválida", (value) => {
                if (!value) return true; // Permite valores nulos
                const [day, month, year] = value.split("/");
                const date = new Date(`${year}-${month}-${day}`);
                return !isNaN(date.getTime()); // Verifica se a data é válida
            }),
    }),

    address: yup.object().shape({
        zipCode: yup.string()
            .nullable()
            .transform((value) => value || null),
        country: yup.string()
            .nullable()
            .transform((value) => value || null),
        state: yup.string()
            .nullable()
            .transform((value) => value || null),
        city: yup.string()
            .nullable()
            .transform((value) => value || null),
        neighborhood: yup.string()
            .nullable()
            .transform((value) => value || null),
        street: yup.string()
            .nullable()
            .transform((value) => value || null),
        complement: yup.string()
            .nullable()
            .transform((value) => value || null),
        residentialNumber: yup.string()
            .nullable()
            .transform((value) => value || null),
    }),
});

const clientUpdateSchema = yup.object().shape({
    completeName: yup.string()
        .nullable()
        .required("O nome completo é obrigatório")
        .max(100, "O nome completo deve ter no máximo 100 caracteres"),

    birthDate: yup.string()
        .nullable()
        .required("A data de nascimento é obrigatória")
        .matches(/^\d{2}\/\d{2}\/\d{4}$/, "Data de nascimento deve estar no formato dd/MM/yyyy")
        .test("is-valid-date", "Data de nascimento inválida", (value) => {
            if (!value) return true; // Permite valores nulos
            const [day, month, year] = value.split("/");
            const date = new Date(`${year}-${month}-${day}`);
            return !isNaN(date.getTime()); // Verifica se a data é válida
        }),

    phone: yup.string()
        .nullable()
        .transform((value) => value || null) // Converte strings vazias para null
        .matches(/\(\d{2}\) \d{4,5}-\d{4}/, "Formato inválido")
        .max(15, "O telefone deve ter no máximo 15 caracteres"),

    email: yup.string()
        .nullable()
        .transform((value) => value || null) // Converte strings vazias para null
        .email("E-mail inválido")
        .max(100, "O e-mail deve ter no máximo 100 caracteres"),

    passport: yup.object().shape({
        number: yup.string()
            .nullable()
            .transform((value) => value || null),
        emissionDate: yup.string()
            .nullable()
            .transform((value) => value || null),
        expirationDate: yup.string()
            .nullable()
            .transform((value) => value || null),
    }),

    address: yup.object().shape({
        zipCode: yup.string()
            .nullable()
            .transform((value) => value || null),
        country: yup.string()
            .nullable()
            .transform((value) => value || null),
        state: yup.string()
            .nullable()
            .transform((value) => value || null),
        city: yup.string()
            .nullable()
            .transform((value) => value || null),
        neighborhood: yup.string()
            .nullable()
            .transform((value) => value || null),
        street: yup.string()
            .nullable()
            .transform((value) => value || null),
        complement: yup.string()
            .nullable()
            .transform((value) => value || null),
        residentialNumber: yup.string()
            .nullable()
            .transform((value) => value || null),
    }),
});

interface ClientFormModalProps {
    isOpen: boolean;
    onClose: () => void;
    isEditing: boolean;
    clientId: number | null;
}

const FormClient: React.FC<ClientFormModalProps> = ({onClose, isEditing, clientId }) => {

    const { control, handleSubmit, formState: { errors }} = useForm<ClientRequest>({
        resolver: yupResolver(clientSchema as unknown as yup.SchemaOf<ClientRequest>),
    });

    const [passportCollapse, setPassportCollapse] = useState(false);
    const [addressCollapse, setAddressCollapse] = useState(false); 

    // Hook para salvar o cliente
    const { mutate: saveClient, isPending: isSaving } = useSaveClient(onClose);

    const togglePassportCollapse = () => {
        setPassportCollapse(!passportCollapse);
    };

    const toggleAddressCollapse = () => {
        setAddressCollapse(!addressCollapse);
    };

    // Carregar dados do cliente se estiver editando
    useEffect(() => {
        if (isEditing && clientId) {
            // Chama hook para buscar cliente pelo id
            console.log("Editando cliente: " + clientId);
        }
    }, [isEditing, clientId]);

    // Função para enviar os dados do formulário
    const onSubmit = (data: ClientRequest) => {
        console.log(data);
        saveClient(data);
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

            <SaveButton disabled={isSaving}/>
        </Box>
    );
}

export default FormClient;