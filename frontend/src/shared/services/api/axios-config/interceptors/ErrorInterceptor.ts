import { AxiosError } from "axios";


export const errorInterceptor = (error : AxiosError) => {
    if (error.response?.status === 401){
        window.location.href = "/login";
        return Promise.reject(error);
    }
    return Promise.reject(error);
}