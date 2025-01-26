import { Box } from '@mui/material';
import NavigationBar from '../../shared/components/navigation-bar/NavigationBar';

export default function Home() {
    return(
        <Box 
            sx={{
                width: "100vw",
                height: "100vh",
                backgroundColor: "background.default"
            }}
        >
           <NavigationBar/>
        </Box>
    );
}