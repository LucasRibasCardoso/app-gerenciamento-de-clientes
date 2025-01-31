import { Box, Typography } from '@mui/material';

import NavigationBar from '../../shared/components/navigation-bar/NavigationBar';
import MapSVG from "../../shared/assets/images/Study abroad-rafiki 1.svg";
import CurrencyTable from '../../shared/components/currency-table/CurrencyTable';

export default function Home() {
    return(
        <Box sx={{
            width: "100vw",
            height: "93vh",
          }}>
           <NavigationBar/>

            <Box sx={{ height: "100%", display: "flex" }}>
                <Box 
                    sx={{ 
                        width: "50%", 
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        justifyContent: "center",
                    }}>

                    <img src={MapSVG} alt="Mapa" style={{ width: "70%" }} />
                    <Typography variant="h2" textAlign="center">
                        Transformando sonhos em destinos inesquecíveis.
                    </Typography>
                </Box>

                <Box 
                    sx={{ 
                        width: "50%", 
                        height: "100%",
                        display: "flex",
                        alignItems:  "center",
                        justifyContent: "center",
                        flexDirection: "column"
                }}>
                    <CurrencyTable/>
                </Box>
            </Box>
        </Box>
    );
}