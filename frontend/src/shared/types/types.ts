
// Formato retornado ao buscar usuários
export type UserResponse = {
    id: string;
    username: string;
    roles: Set<string>;
}; 

// Formato retornando ao fazer busca paginada na api
export type PageResponse<T> = {
    data: T[]; 
    totalPages: number;
    totalElements: number;
  };

export type Passport = {
    number: string | null;
    emissionDate: string | null;
    expirationDate: string | null;
};
export type Address = {
    zipCode: string | null;
    country: string | null;
    state: string | null;
    city: string | null;
    neighborhood: string | null;
    street: string | null;
    complement: string | null;
    residentialNumber: string | null;
};

// Formato de cliente retornado quando buscado na api
export type ClientResponse = {
    id: number;
    completeName: string;
    cpf: string;
    birthDate: string;
    phone: string | null;
    email: string | null;
    passport: Passport;
    address: Address;
};

// Formato utilizado para adicionar clientes 
export type AddClientRequest = {
    completeName: string;
    cpf: string;
    birthDate: string;
    phone: string | null;
    email: string | null;
    passport: Passport;
    address: Address;
}

// Formato utilizado para adicionar clientes 
export type UpdateClientRequest = {
    completeName: string | null;
    birthDate: string | null;
    phone: string | null;
    email: string | null;
    passport: Passport;
    address: Address;
}

// Formato retornado ao fazer login
export type LoginResponse = {
    username: string;
    roles: string[];
    token: string;
};

// Formato de erro padrão
export type GenericError = {
    message: string;
    statusCode: number;
};

// Formato de erro individual para campo de validação de formulário
type ValidationError = {
    field: string;
    message: string;
};

// Formato de erro de validação de formulários quando há mais de um campo com erro
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
