// Tipos de resposta do usuário
export interface IUserResponse {
    id: string;
    username: string;
    roles: Set<string>;
}

// Tipo para a lista de usuários
export type ListOfUser = IUserResponse[];