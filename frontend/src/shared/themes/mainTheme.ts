import { createTheme } from '@mui/material';

const LightTheme = createTheme({
  palette: {
    primary: {
      light: '#eceff1', // 50
      main: '#607d8b', // 500
      dark: '#455a64', // 700
      contrastText: '#ffffff', // texto em botões ou sobre o primário
    },
    secondary: {
      light: '#cfd8dc', // A100
      main: '#78909c', // A400
      dark: '#455a64', // A700
      contrastText: '#000000', // texto em botões ou sobre o secundário
    },
    background: {
      default: '#eceff1', // 50
      paper: '#ffffff', // cor do papel (cards, etc.)
    },
  },
  typography: {
    fontFamily: 'Lato, MontSerrat, Nunito, sans-serif',
  },
});

export default LightTheme;