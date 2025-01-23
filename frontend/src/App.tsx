import { BrowserRouter } from 'react-router-dom'
import { AppRoutes } from './routes'
import { ThemeProvider } from '@mui/material';
import mainTheme  from './shared/themes/mainTheme';

export const App = () => {

  return (
    <ThemeProvider theme={mainTheme}>
      <BrowserRouter>
          <AppRoutes></AppRoutes>
      </BrowserRouter>
    </ThemeProvider>
  )
}

