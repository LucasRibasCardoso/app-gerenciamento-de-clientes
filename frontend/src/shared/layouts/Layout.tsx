import { Box } from "@mui/material";
import NavigationBar from "../components/navigation-bar/NavigationBar";

interface LayoutProps {
  children: React.ReactNode;
  centerBody?: boolean;
}

export default function Layout({ children, centerBody = false }: LayoutProps) {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        minHeight: "100vh", 
      }}
    >
      {/* Barra de navegação fixa no topo */}
      <NavigationBar />

      {/* Corpo da página */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          padding: "15px",
          display: "flex",
          flexDirection: "column",
          justifyContent: centerBody ? "center" : "flex-start",
          alignItems: centerBody ? "center" : "stretch",
        }}
      >
        {children}
      </Box>
    </Box>
  );
}