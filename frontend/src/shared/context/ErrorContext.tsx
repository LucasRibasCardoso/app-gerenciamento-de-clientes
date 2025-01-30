import React, { createContext, useContext, useState } from "react";
import { ErrorSnackbar } from "../components/error-snack-bar/ErrorSnackBar";

interface ErrorContextType {
  errorMessage: string;
  openErrorSnackbar: (message: string) => void;
  closeErrorSnackbar: () => void;
}

const ErrorContext = createContext<ErrorContextType | undefined>(undefined);

export const ErrorProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [openAlert, setOpenAlert] = useState<boolean>(false);

  const openErrorSnackbar = (message: string) => {
    setErrorMessage(message);
    setOpenAlert(true);
  };

  const closeErrorSnackbar = () => {
    setOpenAlert(false);
  };

  return (
    <ErrorContext.Provider value={{ errorMessage, openErrorSnackbar, closeErrorSnackbar }}>
      {children}
      <ErrorSnackbar open={openAlert} message={errorMessage} onClose={closeErrorSnackbar} />
    </ErrorContext.Provider>
  );
};

export const useError = () => {
  const context = useContext(ErrorContext);
  if (!context) {
    throw new Error("useError must be used within an ErrorProvider");
  }
  return context;
};