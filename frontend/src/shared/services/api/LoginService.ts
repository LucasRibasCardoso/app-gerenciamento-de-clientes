import Api from "./axios-config/AxiosConfig";

import { 
    LoginResponse, 
    GenericError, 
    ValidationErrorsResponse, 
    isValidationError, 
    isGenericError 
} from "../../types/types";

// Função para efetuar login
const login = async (
    username: string,
    password: string
): Promise<LoginResponse | GenericError | ValidationErrorsResponse> => {
    try {
        const { data } = await Api.post<LoginResponse>("/auth/login", { username, password });
        return data; 
    } 
    catch (error: any) {

        if (isValidationError(error.response.data)) {
            return {
                message: error.response.data.message || "Erro de validação nos campos enviados.",
                statusCode: error.response.status,
                errors: new Set(error.response.data.errors),
            } as ValidationErrorsResponse;
        }

        if (isGenericError(error.response.data)) {
            return {
                message: error.response.data.message || "Não foi possível realizar login.",
                statusCode: error.response.status,
            } as GenericError;
        }

        return { 
            message: "Ocorreu um erro inesperado ao tentar realizar o login. Verifique sua conexão e tente novamente.", 
            statusCode: 500 
        } as GenericError;
    }
};

export { login };
