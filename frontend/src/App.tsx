import { BrowserRouter } from "react-router-dom"
import { AppRoutes } from "./routes"
import { ThemeProvider } from "@mui/material";
import  LigthTheme   from "./shared/themes/light";
import { ErrorProvider } from "./shared/context/ErrorContext";

export const App = () => {

  return (
    <ErrorProvider>
        <ThemeProvider theme={LigthTheme}>
          <BrowserRouter>
            <AppRoutes></AppRoutes>
          </BrowserRouter>
      </ThemeProvider>
    </ErrorProvider>
  )
}

