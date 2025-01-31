import { createTheme, responsiveFontSizes } from "@mui/material";

// Estende o tipo `Background` para incluir a propriedade `darkGreen`
declare module "@mui/material/styles" {
  interface TypeBackground {
    darkGreen: string;
    buttonHover: string;
  }
}

let LightTheme = createTheme({
  palette: {
    primary: {
      light: "#eceff1",
      main: "#404040",
      dark: "#455a64",
      contrastText: "#ffffff",
    },
    secondary: {
      light: "#cfd8dc",
      main: "#78909c", 
      dark: "#455a64", 
      contrastText: "#404040", 
    },
    error: {
      main: "#b00020"
    },
    background: {
      default: "#fff",
      paper: "#fff",
      darkGreen: "#0D1F23",
      buttonHover: "rgba(255, 255, 255, 0.2)"
    },
  },
  typography: {
    fontFamily: "Lato, Nunito, sans-serif",
    h1: {
      fontFamily: "Lato",
      fontSize: "47.78px",
      color: "#404040",
    },
    h2: {
      fontFamily: "Lato",
      fontSize: "39.81px",
      color: "#404040",
    },
    h3: {
      fontFamily: "Lato",
      fontSize: "33.18px",
      color: "#404040",
    },
    h4: {
      fontFamily: "Lato",
      fontSize: "27.65px",
      color: "#404040",
    },
    body1: {
      fontFamily: "Lato",
      fontSize: "16px",
      color: "#404040",
    },
    button: {
      fontFamily: "Nunito",
      fontSize: "16px",
      fontWeight: "bold",
    },
  },
});

// Adiciona responsividade às fontes
LightTheme = responsiveFontSizes(LightTheme);

export default LightTheme;