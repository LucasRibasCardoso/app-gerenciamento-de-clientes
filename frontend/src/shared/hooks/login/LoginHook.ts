import { useMutation } from "@tanstack/react-query";
import { login } from "../../services/api/login/LoginService";
import { LoginResponse, GenericError, ValidationErrorsResponse, isGenericError } from "../../types/types";

const useLogin = () => {
    return useMutation<LoginResponse, GenericError | ValidationErrorsResponse, { username: string; password: string }>({
        mutationFn: async ({ username, password }) => {
            const response = await login(username, password);
            
            if (isGenericError(response)) {
                throw response;
            }

            return response;
        },
        onSuccess: (data) => {
            localStorage.setItem("token", data.token);
        },
    });
};
export { useLogin };
