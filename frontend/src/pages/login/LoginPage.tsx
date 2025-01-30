import { Box, Typography } from "@mui/material";
import AirplaneSVG from "../../shared/assets/images/airplane-around-world.svg";
import LoginForm from "../../shared/components/form-login/FormLogin";

export default function Login() {
  document.title = "Login - Client Management";

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
        <LoginForm/>
      </Box>

    </Box>
  );
}
