import { useForm, Controller } from "react-hook-form";
import { useState } from "react";
import { TextField, Button, Box, Typography, Snackbar, Alert  } from "@mui/material";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useNavigate } from "react-router-dom";

import { useLogin } from "../../hooks/login/LoginHook";
import { isValidationError, isGenericError } from "../../types/types";

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
  const [openAlert, setOpenAlert] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string>("");

  // Configuração do React Hook Form com validação do Yup
  const {
    control,
    handleSubmit,
    formState: { errors },
    setError
  } = useForm<FormData>({
    resolver: yupResolver(schema),
  });

  // Função chamada ao enviar o formulário
  const onSubmit = async (data: FormData) => {
    try {
      await login.mutateAsync({
        username: data.username,
        password: data.password
      });
      navigate("/home");
    }
    catch (error) {
      // Para cada erro de validação, define o erro no campo correspondente
      if (isValidationError(error)) {
        error.errors.forEach((erro) => {
          setError(erro.field as keyof FormData, {
            message: erro.message
          });
        });
      }
      else if (isGenericError(error)) {
        setErrorMessage(error.message);
        setOpenAlert(true);
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

      {/* Campo de Username */}
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

      {/* Campo de Password */}
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

      {/* Botão de Login */}
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

      {/* PopUp em caso de erro */}
      <Snackbar
        open={openAlert}
        autoHideDuration={5000} // O alerta será fechado após 5 segundos
        onClose={() => setOpenAlert(false)}
        anchorOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
      >
        <Alert severity="error" onClose={() => setOpenAlert(false)}>
          {errorMessage}
        </Alert>
      </Snackbar>
    </Box>
  );
}