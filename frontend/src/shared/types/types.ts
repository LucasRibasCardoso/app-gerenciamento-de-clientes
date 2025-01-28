
export type UserResponse = {
    id: string;
    username: string;
    roles: Set<string>;
}; 

// Tipo para a lista de usuários
export type ListOfUser = UserResponse[];

export type PassportResponse = {
    number: string | null;
    emissionDate: string | null;
    expirationDate: string | null;
};

export type AddressResponse = {
    zipCode: string | null;
    country: string | null;
    state: string | null;
    city: string | null;
    neighborhood: string | null;
    street: string | null;
    complement: string | null;
    residentialNumber: string | null;
};

export type ClientResponse = {
    id: number;
    completeName: string;
    cpf: string;
    birthDate: string | null;
    phone: string | null;
    email: string | null;
    passport: PassportResponse | null;
    address: AddressResponse | null;
};

export type ListOfClient = ClientResponse[];

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

// Type guards para verificar os tipos de erro
export function isValidationError(error: unknown): error is ValidationErrorsResponse {
    return (
        error !== null &&
        typeof error === "object" &&
        "errors" in error &&
        "statusCode" in error
    );
  }

export function isGenericError(error: unknown): error is GenericError {
    return (
        error !== null &&
        typeof error === "object" &&
        "statusCode" in error &&
        "message" in error &&
        !("errors" in error)
    );
}