import { Box, Typography, Snackbar, Alert } from "@mui/material";
import AirplaneSVG from "../../shared/assets/images/airplane-around-world.svg";
import LoginForm from "../../shared/components/form-login/FormLogin";
import { useState } from "react";

export default function Login() {
  const [openAlert, setOpenAlert] = useState(false);
  const [errorMessage, setErrorMessage] = useState<string>("");

  const handleError = (message: string) => {
    setErrorMessage(message);
    setOpenAlert(true);
  };

  return (
    <Box
      sx={{
        display: "flex",
        width: "100vw",
        height: "100vh",
      }}
    >
      <Box
        sx={{
          backgroundColor: "background.default",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          width: "50%",
          gap: "20px",
        }}
      >
        <Typography variant="h1">Seja Bem Vindo</Typography>
        <Typography variant="h2" textAlign={"center"}>
          Gerencie sua agência com facilidade.
          <br />
          Faça login para começar.
        </Typography>
        <img src={AirplaneSVG} alt="imagem avião" />
      </Box>

      <Box
        sx={{
          backgroundColor: "background.darkGreen",
          display: "flex",
          width: "50%",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <LoginForm onError={handleError} />
      </Box>

      {/* Snackbar para exibir erros */}
      <Snackbar
        open={openAlert}
        autoHideDuration={5000}
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
