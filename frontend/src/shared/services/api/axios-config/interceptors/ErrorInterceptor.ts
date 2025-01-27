import { AxiosError } from "axios";


export const errorInterceptor = (error: AxiosError) => {
    if (error.response?.status === 401) {
        const requestURL = error.config?.url || "";
  
        // Verifica se é a rota de login
        if (requestURL.includes("/auth/login")) {
            // Para erros relacionados ao login (como BadCredentials), apenas rejeita o erro
            return Promise.reject(error);
        }
        // Caso contrário (token expirado), redireciona para login
        localStorage.setItem("logoutMessage", "Sua sessão expirou. Faça login novamente.");
        setTimeout(() => {
            window.location.href = "/login";
        }, 200);
        return Promise.reject(error);
    }
  
    // Outros erros continuam sendo tratados normalmente
    return Promise.reject(error);
  };