import Api from "../axios-config/AxiosConfig";
import { LoginResponse, GenericError, ValidationErrorsResponse } from "../../../types/types";

// Função para efetuar login
const login = async (
    username: string,
    password: string
): Promise<LoginResponse | GenericError | ValidationErrorsResponse> => {
    try {
        const { data } = await Api.post<LoginResponse>("auth/login", { username, password });
        return data; 
    } catch (error: any) {
        // Captura o erro e verifica o formato
        if (error.response) {
            const statusCode = error.response.status || 500;
            const responseData = error.response.data;

            // Verifica se o erro é do tipo ValidationErrorsResponse
            if (responseData.errors && Array.isArray(responseData.errors)) {
                return {
                    message: responseData.message || 
                        "Um ou mais campos apresentam erros de validação. Por favor, corrija e tente novamente.",
                    statusCode,
                    errors: new Set(
                        responseData.errors.map((err: any) => ({
                            field: err.field,
                            message: err.message,
                        }))
                    ),
                } as ValidationErrorsResponse;
            }

            // Caso contrário, trata como um erro genérico
            return {
                message: responseData.message || 
                    "Não foi possível realizar o login. Verifique suas credenciais ou tente novamente mais tarde.",
                statusCode,
            } as GenericError;
        }

        // Em caso de falha de rede ou erro desconhecido
        return { 
            message: "Ocorreu um erro inesperado ao tentar realizar o login. Verifique sua conexão e tente novamente.", 
            statusCode: 500 
        } as GenericError;
    }
};

export { login };
