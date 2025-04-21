import { DashboardResponse, GenericError, isGenericError } from "../../types/types";
import Api from "./axios-config/AxiosConfig";

const GetDashboardData = async (): Promise<DashboardResponse | GenericError> => {
    try {
        const url = "/dashboard/data";
        const response = await Api.get<DashboardResponse>(url);
        return response.data;
    } catch (error: any) {
        if (error.response) {
            if (isGenericError(error.response.data)) {
                return {
                    message:
                        error.response.data.message ||
                        "Não foi possível carregar os dados do dashboard.",
                    statusCode: error.response.status,
                } as GenericError;
            }
        }
        return {
            message:
                "Ocorreu um erro inesperado ao tentar carregar os dados do dashboard.",
            statusCode: 500,
        } as GenericError;
    }
};

export { GetDashboardData };
