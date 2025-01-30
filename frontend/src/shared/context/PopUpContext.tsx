import React, { createContext, useContext, useState } from "react";
import { Snackbar, Alert, AlertColor } from "@mui/material";

interface PopUpContextType {
  showMessage: (message: string, severity: AlertColor) => void;
  hideMessage: () => void;
}

const PopUpContext = createContext<PopUpContextType | undefined>(undefined);

export const PopUpProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [severity, setSeverity] = useState<AlertColor>("error");

  const showMessage = (message: string, severity: AlertColor) => {
    setMessage(message);
    setSeverity(severity);
    setOpen(true);
  };

  const hideMessage = () => {
    setOpen(false);
  };

  return (
    <PopUpContext.Provider value={{ showMessage, hideMessage }}>
      {children}
      <Snackbar
        open={open}
        autoHideDuration={5000}
        onClose={hideMessage}
        anchorOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
      >
        <Alert severity={severity} onClose={hideMessage}>
          {message}
        </Alert>
      </Snackbar>
    </PopUpContext.Provider>
  );
};

export const usePopUp = () => {
  const context = useContext(PopUpContext);
  if (!context) {
    throw new Error("Erro no componente de PopUp");
  }
  return context;
};