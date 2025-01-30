import { useForm, Controller } from "react-hook-form";
import { TextField, Button, Box, Typography } from "@mui/material";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useNavigate } from "react-router-dom";

import { useLogin } from "../../hooks/login/LoginHook";
import { isValidationError, isGenericError } from "../../types/types";
import { usePopUp } from "../../context/PopUpContext";

// Definição do esquema de validação com Yup
const schema = yup.object({
  username: yup
    .string()
    .required("O usuário é obrigatório.")
    .min(6, "O usuário deve ter no mínimo 6 caracteres."),
  password: yup
    .string()
    .required("A senha é obrigatória.")
    .min(8, "A senha deve ter no mínimo 8 caracteres.")
    .matches(/[\W_]/, "A senha deve conter pelo menos um caractere especial."),
});

interface FormData {
  username: string;
  password: string;
}

export default function FormLogin() {
  const navigate = useNavigate();
  const login = useLogin();
  const { showMessage } = usePopUp();

  const {
    control,
    handleSubmit,
    formState: { errors },
    setError,
  } = useForm<FormData>({
    resolver: yupResolver(schema),
  });

  const onSubmit = async (data: FormData) => {
    try {
      await login.mutateAsync({
        username: data.username,
        password: data.password,
      });
      navigate("/home");
    } 
    catch (error) {
      if (isValidationError(error)) {
        error.errors.forEach((erro) => {
          setError(erro.field as keyof FormData, {
            message: erro.message,
          });
        });
      } 
      else if (isGenericError(error)) {
        showMessage(error.message, "error");
      }
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
        width: "100%",
        maxWidth: "450px",
        margin: "auto",
        backgroundColor: "background.default",
        padding: "15px",
        borderRadius: "10px",
      }}
    >
      <Typography variant="h2" textAlign="center">
        LOGIN
      </Typography>

      <Controller
        name="username"
        control={control}
        defaultValue=""
        render={({ field }) => (
          <TextField
            {...field}
            label="Usuário"
            variant="outlined"
            fullWidth
            error={!!errors.username}
            helperText={errors.username?.message}
          />
        )}
      />

      <Controller
        name="password"
        control={control}
        defaultValue=""
        render={({ field }) => (
          <TextField
            {...field}
            label="Senha"
            type="password"
            variant="outlined"
            fullWidth
            error={!!errors.password}
            helperText={errors.password?.message}
          />
        )}
      />

      <Button
        type="submit"
        variant="contained"
        loading={login.isPending}
        loadingPosition="start"
        sx={{
          width: "100%",
          backgroundColor: "background.darkGreen",
        }}
      >
        Entrar
      </Button>
    </Box>
  );
}
