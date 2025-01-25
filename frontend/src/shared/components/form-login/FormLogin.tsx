import { useForm, Controller } from "react-hook-form";
import { TextField, Button, Box, Typography } from "@mui/material";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";

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

function SignIn() {
  // Configuração do React Hook Form com validação do Yup
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>({
    resolver: yupResolver(schema),
  });

  // Função chamada ao enviar o formulário
  const onSubmit = (data: FormData) => {
    // Enviar requisição para o backend e salvar o token no Local Storage
    console.log("Username: ", data.username);
    console.log("Password: ", data.password);
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

export default SignIn;
