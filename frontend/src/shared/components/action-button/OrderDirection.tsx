import * as React from "react";
import { IconButton } from "@mui/material";
import ArrowUpwardOutlinedIcon from '@mui/icons-material/ArrowUpwardOutlined';
import ArrowDownwardOutlinedIcon from '@mui/icons-material/ArrowDownwardOutlined';

const OrderDirection = () => {
    const [orderDirection, setOrderDirection] = React.useState<"asc" | "desc">("asc"); 

    const toggleOrderDirection = () => {
        const newDirection = orderDirection === "asc" ? "desc" : "asc";
        setOrderDirection(newDirection); 
        console.log("Ordenar em ordem:", newDirection);
    };

    return (
        <IconButton
            onClick={toggleOrderDirection}
            sx={{
                borderColor: "#404040",
                border: "1px solid #404040",
                borderRadius: "5px",
            }}
        >
        {orderDirection === "asc" ? (
            <ArrowUpwardOutlinedIcon sx={{ color: "green" }} />
        ) : (
            <ArrowDownwardOutlinedIcon sx={{ color: "red" }} />
        )}
        </IconButton>
    );
};

export default OrderDirection;