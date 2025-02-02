import { useState } from "react";
import { 
    Box, 
    Typography, 
    TextField, 
    Button, 
    Collapse, 
    IconButton 
} from "@mui/material";
import { ExpandMore, ExpandLess } from "@mui/icons-material";
import { useForm, Controller } from "react-hook-form";
import { useParams } from "react-router-dom";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import Grid from "@mui/material/Grid2";
import InputMask from "react-input-mask";


import Layout from "../../shared/layouts/Layout";
import { ClientRequest } from "../../shared/types/types";
import { SaveButton } from "../../shared/components/buttons";


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
        .matches(/^\d{2}\/\d{2}\/\d{4}$/, "Data de nascimento deve estar no formato dd/MM/yyyy"),

    phone: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .matches(/\(\d{2}\) \d{4,5}-\d{4}/, "Formato inválido")
        .max(15, "O telefone deve ter no máximo 15 caracteres")
        .default(null),

    email: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .email("E-mail inválido")
        .max(100, "O e-mail deve ter no máximo 100 caracteres")
        .default(null),

    passport: yup.object().shape({
      number: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
      emissionDate: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
      expirationDate: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
    }).default(null),

    address: yup.object().shape({
      zipCode: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
      country: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
      state: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
      city: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
      neighborhood: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
      street: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
      complement: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
      residentialNumber: yup.string()
        .nullable()
        .transform((value) => value === "" ? null : value)
        .default(null),
    }).default(null),
});

export default function FormSaveClient() {
    const { id } = useParams();
    let titleForm = id ? "Editar Cliente" : "Cadastrar Cliente";

    const { control, handleSubmit, formState: { errors }} = useForm<ClientRequest>({
        resolver: yupResolver(clientSchema as unknown as yup.SchemaOf<ClientRequest>),
    });

    const [passportCollapse, setPassportCollapse] = useState(false);
    const [addressCollapse, setAddressCollapse] = useState(false); 

    const togglePassportCollapse = () => {
        setPassportCollapse(!passportCollapse);
    };

    const toggleAddressCollapse = () => {
        setAddressCollapse(!addressCollapse);
    };

    const onSubmit = (data: ClientRequest) => {
        console.log(data);
    };

    return (
        <Layout>
            <Box
                component="form"
                onSubmit={handleSubmit(onSubmit)}
                sx={{
                    display: "flex",
                    flexDirection: "column",
                    gap: "25px",
                    width: "100%",
                    maxWidth: "650px",
                    margin: "auto",
                    padding: "20px",
                }}>
                <Typography variant="h1" textAlign="center" mb={"30px"}>
                    {titleForm}
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

                <SaveButton />
            </Box>
        </Layout>
    );
}