import axios from "axios";
import { errorInterceptor } from "./interceptors/ErrorInterceptor";

const Api = axios.create({
	baseURL: import.meta.env.VITE_API_BACKEND_URL,
});

// Interceptor de requisição: adiciona o token ao cabeçalho
Api.interceptors.request.use(
	(config) => {
		const token = localStorage.getItem("token");
		if (token) {
			config.headers.Authorization = `Bearer ${token}`;
		}
		return config;
	},
	(error) => Promise.reject(error)
);

// Interceptor de resposta: redireciona para o login caso usuário não esteja autenticado
Api.interceptors.response.use((response) => response, errorInterceptor);

export default Api;
