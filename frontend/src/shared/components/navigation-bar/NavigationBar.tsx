import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import AirplanemodeActiveIcon from '@mui/icons-material/AirplanemodeActive';
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import GroupsOutlinedIcon from '@mui/icons-material/GroupsOutlined';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';

// Tipagem para as props do NavButton
interface NavButtonProps {
    href: string;
    icon: React.ReactNode;
    label: string;
  }
  
  // Componente reutilizável para os botões
  const NavButton: React.FC<NavButtonProps> = ({ href, icon, label }) => (
    <Button
         sx={{ my: 2, color: 'white', fontWeight: 600 ,
             '&:hover': {
                 backgroundColor: 'rgba(255, 255, 255, 0.2)', // Cor de fundo ao passar o mouse
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
        { label: 'HOME', href: '/login', icon: <HomeOutlinedIcon /> },
        { label: 'CLIENTES', href: '/clientes', icon: <GroupsOutlinedIcon /> },
    ];

    return (
        <AppBar position="static" sx={{background: '#0D1F23'}}>
            <Container maxWidth={false} >
                <Toolbar disableGutters sx={{ display: 'flex', justifyContent: 'space-between' }}>
                    {/* Logo e título */}
                    <Box sx={{ display: { xs: 'none', md: 'flex', alignItems: 'center' } }}>
                        <AirplanemodeActiveIcon sx={{ mr: 1 }} />
                        <Typography
                            variant="h6"
                            noWrap
                            sx={{
                                mr: 2,
                                fontFamily: 'Lato',
                                fontWeight: 700,
                                letterSpacing: '.2rem',
                                color: 'inherit',
                                textDecoration: 'none',
                            }}>
                            CR VIAGENS E TURISMO
                        </Typography>
                    </Box>

                    {/* Navegação */}
                    <Box sx={{ display: 'flex', gap: 5 }}>
                        {navItems.map((item, index) => (
                        <NavButton key={index} href={item.href} icon={item.icon} label={item.label} />
                        ))}
                    </Box>

                    {/* Logout */}
                    <NavButton href="#logout" icon={<LogoutOutlinedIcon />} label="LOGOUT" />
                </Toolbar>
            </Container>
        </AppBar>
    );
}