import { useMutation } from "@tanstack/react-query";
import { login } from "../services/api/LoginService";
import { useNavigate } from "react-router-dom";
import { 
    LoginResponse, 
    GenericError, 
    ValidationErrorsResponse,
    isGenericError, 
    isValidationError 
} from "../types/types";

import { usePopUp } from "../context/PopUpContext";

const useLogin = () => {
    const navigate = useNavigate();
    const { showMessage } = usePopUp();

    return useMutation<LoginResponse, GenericError | ValidationErrorsResponse, { username: string; password: string }>({
        mutationFn: async ({ username, password }) => {
            const response = await login(username, password);
            
            if (isGenericError(response) || isValidationError(response)) {
                throw response;
            }
            return response;
        },
        onSuccess: (data) => {
            localStorage.setItem("token", data.token);
            navigate("/home");
        },
        onError: (error) => {
            showMessage(error.message, "error");
        }
    });
};
export { useLogin };
