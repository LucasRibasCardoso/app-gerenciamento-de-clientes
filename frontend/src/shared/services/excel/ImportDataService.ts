import { read, utils } from "xlsx";

interface ValidationResult {
    valid: boolean;
    errors: string[];
}

interface ProcessExcelOptions {
    dateFormat?: string;
}

// Função para processar o arquivo Excel e retornar os dados em formato JSON
export const processExcelFile = (
    fileData: ArrayBuffer,
    options: ProcessExcelOptions = {}
) => {
    const { dateFormat = "dd/mm/yyyy" } = options;

    // Ler o arquivo Excel
    const workbook = read(fileData, {
        type: "array",
        cellDates: true,
        dateNF: dateFormat,
    });

    // Verificar se existem planilhas
    const firstSheetName = workbook.SheetNames[0];
    if (!firstSheetName) {
        throw new Error("Arquivo sem planilhas válidas");
    }

    const worksheet = workbook.Sheets[firstSheetName];

    // Converter para JSON com formatação de datas
    const jsonData = utils.sheet_to_json(worksheet, {
        raw: false, // Não retorna valores brutos
        dateNF: dateFormat, // Força a formatação de datas
    });

    if (jsonData.length === 0) {
        throw new Error("O arquivo não contém dados válidos");
    }

    return jsonData;
};

// Valida os dados importados usando o validador fornecido
export const validateImportedData = (
    data: any[],
    validator?: (data: any[]) => ValidationResult
): ValidationResult => {
    if (!validator) {
        return { valid: true, errors: [] };
    }

    return validator(data);
};

// Método que processa e valida os dados em uma única operação
export const processAndValidateExcelFile = (
    fileData: ArrayBuffer,
    validator?: (data: any[]) => ValidationResult,
    options: ProcessExcelOptions = {}
): { data: any[]; validationResult: ValidationResult } => {
    // Processa o arquivo Excel para obter os dados
    const jsonData = processExcelFile(fileData, options);

    // Valida os dados usando o validador fornecido
    const validationResult = validateImportedData(jsonData, validator);

    return {
        data: jsonData,
        validationResult,
    };
};
