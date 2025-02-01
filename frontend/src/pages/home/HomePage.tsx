import { Box, Typography } from '@mui/material';

import MapSVG from "../../shared/assets/images/Study abroad-rafiki 1.svg";
import CurrencyTable from '../../shared/components/currency-table/CurrencyTable';
import Layout from '../../shared/layouts/Layout';

export default function Home() {
    return(
        <Layout centerBody={true}>
            <Box 
                sx={{ 
                    width: "100%",
                    display: "flex", 
                    alignItems: "center",
                    gap: "20px"
                }}>
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
        </Layout>
    );
}