import { BrowserRouter } from "react-router-dom"
import { AppRoutes } from "./routes"
import { ThemeProvider } from "@mui/material";
import  LigthTheme   from "./shared/themes/light";
import { PopUpProvider } from "./shared/context/PopUpContext";

export const App = () => {

  return (
    <PopUpProvider>
        <ThemeProvider theme={LigthTheme}>
          <BrowserRouter>
            <AppRoutes></AppRoutes>
          </BrowserRouter>
      </ThemeProvider>
    </PopUpProvider>
  )
}

