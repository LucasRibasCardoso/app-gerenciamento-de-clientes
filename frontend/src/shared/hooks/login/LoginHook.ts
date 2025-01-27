import { useMutation } from "@tanstack/react-query";
import { login } from "../../services/api/login/LoginService";
import { LoginResponse, GenericError, ValidationErrorsResponse } from "../../types/types";

const useLogin = () => {
    return useMutation<LoginResponse, GenericError | ValidationErrorsResponse, { username: string; password: string }>({
        mutationFn: async ({ username, password }) => {
            const result = await login(username, password);
            
            if ("statusCode" in result) {
                throw result;
            }

            return result;
        },
        onSuccess: (data) => {
            localStorage.setItem("token", data.token);
        },
    });
};
export { useLogin };
