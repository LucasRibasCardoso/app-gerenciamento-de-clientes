import React from "react";
import { Snackbar } from "@mui/material";
import MuiAlert, { AlertProps } from "@mui/material/Alert";

const Alert = React.forwardRef<HTMLDivElement, AlertProps>(function Alert(
  props,
  ref,
) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

interface PopUpProps {
  open: boolean;
  message: string;
  onClose: () => void;
}

const PopUp: React.FC<PopUpProps> = ({ open, message, onClose }) => {
  return (
    <Snackbar
      open={open}
      autoHideDuration={5000}
      onClose={onClose}
      anchorOrigin={{
        vertical: "top",
        horizontal: "right",
      }}
    >
      <Alert severity="error" onClose={onClose}>
        {message}
      </Alert>
    </Snackbar>
  );
};

export { PopUp };