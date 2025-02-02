import { useNavigate } from "react-router-dom";
import LogoutOutlinedIcon from "@mui/icons-material/LogoutOutlined";
import { Button } from "@mui/material";

const LogoutButton = () => {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <Button 
        onClick={logout} 
        startIcon={<LogoutOutlinedIcon />}
        sx={{ my: 2, color: "white", fontWeight: 600 ,
            "&:hover": {
               backgroundColor: "background.buttonHover",
            },
        }}
    >
      LOGOUT
    </Button>
  );
};

export default LogoutButton;