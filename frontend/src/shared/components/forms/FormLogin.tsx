import { useForm, Controller } from "react-hook-form";
import { TextField, Button, Box, Typography } from "@mui/material";
import { yupResolver } from "@hookform/resolvers/yup";

import { useLogin } from "../../hooks/LoginHook";
import { LoginSchema } from "./schemas";

interface FormData {
  username: string;
  password: string;
}

export default function FormLogin() {
  const login = useLogin();

  const { control, handleSubmit, formState: { errors } } = useForm<FormData>({
    resolver: yupResolver(LoginSchema),
  });

  const onSubmit = async (data: FormData) => {
    await login.mutateAsync({ username: data.username, password: data.password});
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