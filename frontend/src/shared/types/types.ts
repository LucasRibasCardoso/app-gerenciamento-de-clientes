
export type UserResponse = {
    id: string;
    username: string;
    roles: Set<string>;
}; 

// Tipo para a lista de usuários
export type ListOfUser = UserResponse[];

export type LoginResponse = {
    username: string;
    roles: string[];
    token: string;
};

export type GenericError = {
    message: string;
    statusCode: number;
};

// Formato de erro de validação
export type ValidationError = {
    field: string; // Nome do campo que causou o erro
    message: string; // Mensagem de erro para o campo
};

export type ValidationErrorsResponse = {
    message: string;
    statusCode: number;
    errors: Set<ValidationError>;
};