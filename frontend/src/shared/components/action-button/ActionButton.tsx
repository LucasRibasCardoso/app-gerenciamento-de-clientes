import { Button, ButtonProps } from '@mui/material';
import { ReactNode } from 'react';

interface ActionButtonProps extends ButtonProps {
  icon: ReactNode;
  text: string;
  hoverColor: string;
  borderColor: string;
  iconColor: string;
}

const ActionButton: React.FC<ActionButtonProps> = ({ icon, text, hoverColor, borderColor, iconColor, ...props }) => (
  <Button
    variant="outlined"
    sx={{
      borderColor,
      ":hover": {
        background: hoverColor,
        borderColor: hoverColor,
        color: "#fff",
        "& .MuiSvgIcon-root": {
          color: "#fff",
        },
      },
    }}
    {...props}
  >
    {icon}
    {text}
  </Button>
);

export default ActionButton;
