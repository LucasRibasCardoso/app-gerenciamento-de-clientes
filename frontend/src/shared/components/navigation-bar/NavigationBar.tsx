import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import AirplanemodeActiveIcon from "@mui/icons-material/AirplanemodeActive";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import GroupsOutlinedIcon from "@mui/icons-material/GroupsOutlined";

import LogoutButton from "../action-button/LogoutButton";

// Tipagem para as props do NavButton
interface NavButtonProps {
    href: string;
    icon: React.ReactNode;
    label: string;
  }
  
  const NavButton: React.FC<NavButtonProps> = ({ href, icon, label }) => (
    <Button
         sx={{ my: 2, color: "white", fontWeight: 600 ,
             "&:hover": {
                backgroundColor: "background.buttonHover",
             },
         }}
         startIcon={icon}
         href={href}
         aria-label={label.toLowerCase()}
         >
         {label}
    </Button>
  );

export default function NavigationBar() {
    const navItems = [
        { label: "HOME", href: "/home", icon: <HomeOutlinedIcon /> },
        { label: "CLIENTES", href: "/clientes", icon: <GroupsOutlinedIcon /> },
    ];

    return (
        <AppBar 
            sx={{
                backgroundColor: "background.darkGreen",
                position: "static",
                height: "7vh",
                minHeight: "65px"
            }}
        >
            <Container disableGutters maxWidth={false}>
                <Toolbar 
                    sx={{ 
                        display: "flex", 
                        justifyContent: "space-between",
                        width: "100%"
                    }}
                >
                    {/* Logo e título */}
                    <Box 
                        sx={{ 
                            display: "flex", 
                            alignItems: "center"
                        }}>
                        <AirplanemodeActiveIcon sx={{ mr: 1 }} />
                        <Typography
                            variant="h6"
                            noWrap
                            sx={{
                                letterSpacing: "0.2rem",
                            }}>
                            CR VIAGENS E TURISMO
                        </Typography>
                    </Box>

                    {/* Navegação */}
                    <Box sx={{ display: "flex", gap: "15px" }}>
                        {navItems.map((item, index) => (
                        <NavButton key={index} href={item.href} icon={item.icon} label={item.label} />
                        ))}
                    </Box>

                    {/* Logout */}
                    <LogoutButton/>
                </Toolbar>
            </Container>
        </AppBar>
    );
}