import { Button } from "@mui/material";

interface ActionButtonProps {
  icon: React.ReactNode;
  text: string;
  hoverColor: string;
  borderColor: string;
  iconColor: string;
  id: string; 
  onClick: (id: string) => void; 
}

interface ActionButtonProps {
  icon: React.ReactNode;
  text: string;
  hoverColor: string;
  borderColor: string;
  iconColor: string;
  id: string;
  onClick: (id: string) => void; 
}

const ActionButton: React.FC<ActionButtonProps> = 
  ({ icon, text, hoverColor, borderColor, iconColor, id, onClick, ...props }) => (
  <Button
    variant="outlined"
    sx={{
      borderColor,
      ":hover": {
        background: hoverColor,
        borderColor: hoverColor,
        color: "primary.contrastText",
        "& .MuiSvgIcon-root": {
          color: "primary.contrastText",
        },
      },
    }}
    onClick={() => onClick(id)}  // Passa o id para a função onClick
    {...props}
  >
    {icon}
    {text}
  </Button>
);


export default ActionButton;
