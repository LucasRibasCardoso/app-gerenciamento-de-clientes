import Api from "../axios-config/AxiosConfig";
import { ListOfUser } from "../../../types/types";

// Função para buscar todos os usuários
const findAllUsers = async (): Promise<ListOfUser> => {
    const { data } = await Api.get("/users");
    return data;
};
// Função para deletar um usuário
const deleteUserById = async (userID: string): Promise<void> => {
    await Api.delete(`/users/${userID}`);
};

export { findAllUsers, deleteUserById };