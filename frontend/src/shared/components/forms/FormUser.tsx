import { useEffect } from "react";
import {
    Box,
    Typography,
    IconButton,
    MenuItem,
    Select,
    FormControl,
    InputLabel,
    FormHelperText,
} from "@mui/material";
import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import Grid from "@mui/material/Grid2";
import CloseIcon from "@mui/icons-material/Close";

import { AddUserRequest, UpdateUserRequest, UserFormData } from "../../types/types";
import { SaveButton } from "../buttons";
import { useCreateUser, useUpdateUser, useGetUserById } from "../../hooks/UserHook";
import { usePopUp } from "../../context/PopUpContext";
import {
    AddUserSchema,
    AddUserSchemaDefaultValues,
    UpdateUserSchema,
    UpdateUserSchemaDefaultValues,
} from "./schemas/UserSchema";
import { TextInput } from "../inputs";

interface UserFormModalProps {
    isOpen: boolean;
    onClose: () => void;
    isEditing: boolean;
    userId: string | null;
}

const getModifiedFields = (dirtyFields: Record<string, any>, data: any) => {
    const result: any = {};

    // Itera sobre as chaves dos campos marcados como modificados
    Object.keys(dirtyFields).forEach((key) => {
        // Verifica se o campo foi modificado e copia seu valor para o resultado
        if (dirtyFields[key] === true) {
            result[key] = data[key];
        }
    });

    return result;
};

const FormUser: React.FC<UserFormModalProps> = ({ onClose, isEditing, userId }) => {
    // Seleciona o schema e os valores padrão de acordo com o modo
    const schema = isEditing ? UpdateUserSchema : AddUserSchema;
    const defaultValues = isEditing
        ? UpdateUserSchemaDefaultValues
        : AddUserSchemaDefaultValues;

    const {
        control,
        reset,
        handleSubmit,
        formState: { errors, isDirty, dirtyFields },
    } = useForm<UserFormData>({
        resolver: yupResolver(schema) as any,
        defaultValues,
        mode: "onChange",
    });

    const { showMessage } = usePopUp();

    // Hook para salvar usuário
    const { mutate: createUser, isPending: isCreating } = useCreateUser(onClose);

    // Hook para atualizar usuário
    const { mutate: updateUser, isPending: isUpdating } = useUpdateUser(onClose);

    // Hook para buscar usuário por ID se estiver no modo edição
    const { data: selectedUser } = useGetUserById(userId!);

    // Preenche o formulário com os dados do usuário quando eles são carregados para edição
    useEffect(() => {
        if (isEditing && selectedUser) {
            reset(selectedUser);
        }
    }, [selectedUser, isEditing, reset]);

    // Função para enviar os dados do formulário
    const onSubmit = (data: UserFormData) => {
        if (isEditing && userId) {
            if (!isDirty) {
                showMessage("Nenhuma alteração foi detectada.", "info");
                return;
            }

            const modifiedFields = getModifiedFields(dirtyFields, data);
            updateUser({
                userId,
                data: modifiedFields as UpdateUserRequest,
            });
        } else {
            createUser(data as AddUserRequest);
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
                padding: "20px",
            }}
        >
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

            <Typography
                variant="h1"
                textAlign="center"
                mb={"30px"}
            >
                {isEditing ? "Editar Usuário" : "Cadastrar Usuário"}
            </Typography>

            <Grid
                container
                spacing={2}
            >
                <Grid size={12}>
                    <TextInput
                        name="username"
                        control={control}
                        label="Nome de Usuário"
                        errors={errors}
                    />
                </Grid>

                {!isEditing && (
                    <Grid size={12}>
                        <TextInput
                            name="password"
                            control={control}
                            label="Senha"
                            type="text"
                            errors={errors}
                        />
                    </Grid>
                )}

                <Grid size={12}>
                    <Controller
                        name="role"
                        control={control}
                        render={({ field }) => (
                            <FormControl
                                fullWidth
                                error={!!errors.role}
                            >
                                <InputLabel id="role-select-label">
                                    Tipo de Usuário
                                </InputLabel>
                                <Select
                                    {...field}
                                    labelId="role-select-label"
                                    label="Tipo de Usuário"
                                    value={field.value || ""}
                                >
                                    <MenuItem value="USER">USER</MenuItem>
                                    <MenuItem value="MANAGER">MANAGER</MenuItem>
                                    <MenuItem value="ADMIN">ADMIN</MenuItem>
                                </Select>
                                {errors.role && (
                                    <FormHelperText>{errors.role.message}</FormHelperText>
                                )}
                            </FormControl>
                        )}
                    />
                </Grid>
            </Grid>

            <SaveButton disabled={isCreating || isUpdating} />
        </Box>
    );
};

export default FormUser;
