import { Button } from "@mui/material";

interface ActionButtonProps {
  icon: React.ReactNode;
  text: string;
  hoverColor: string;
  borderColor: string;
  iconColor: string;
  onClick: () => void; 
  disabled?: boolean;
}

const ActionButton: React.FC<ActionButtonProps> = 
  ({ icon, text, hoverColor, borderColor, iconColor, onClick, disabled, ...props }) => (
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
    onClick={onClick}
    disabled={disabled}
    {...props}
  >
    {icon}
    {text}
  </Button>
);



export default ActionButton;
