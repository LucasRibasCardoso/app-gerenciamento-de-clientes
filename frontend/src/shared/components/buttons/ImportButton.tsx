import { useState } from "react";
import { Button, CircularProgress } from "@mui/material";
import UploadIcon from "@mui/icons-material/Upload";
import { usePopUp } from "../../context/PopUpContext";
import { processAndValidateExcelFile } from "../../services/excel/ImportDataService";

interface ImportButtonProps {
    onImportComplete: (data: any[]) => void;
    validator?: (data: any[]) => { valid: boolean; errors: string[] };
    label?: string;
    accept?: string;
}

const ImportButton: React.FC<ImportButtonProps> = ({
    onImportComplete,
    validator,
    label = "Importar",
    accept = ".xlsx,.xls",
}) => {
    const [loading, setLoading] = useState(false);
    const { showMessage } = usePopUp();

    const handleFileUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        if (!file) return;

        setLoading(true);

        const reader = new FileReader();
        reader.onload = (e) => {
            try {
                // Usar o serviço de importação para processar e validar o arquivo Excel
                const fileData = e.target?.result as ArrayBuffer;
                const { data: jsonData, validationResult } = processAndValidateExcelFile(
                    fileData,
                    validator
                );

                // Verificar resultados da validação
                if (!validationResult.valid) {
                    showMessage(
                        `Erro na validação: ${validationResult.errors.join(", ")}`,
                        "error"
                    );
                    setLoading(false);
                    return;
                }

                // Retornar os dados processados
                onImportComplete(jsonData);
                showMessage("Importação realizada com sucesso!", "success");
            } catch (error) {
                showMessage(
                    error instanceof Error
                        ? error.message
                        : "Erro ao processar o arquivo. Verifique o formato.",
                    "error"
                );
            } finally {
                setLoading(false);
                // Limpar o input para permitir nova seleção do mesmo arquivo
                event.target.value = "";
            }
        };

        reader.readAsArrayBuffer(file);
    };

    return (
        <>
            <input
                accept={accept}
                id="import-file"
                type="file"
                onChange={handleFileUpload}
                style={{ display: "none" }}
            />
            <label htmlFor="import-file">
                <Button
                    component="span"
                    variant="contained"
                    startIcon={
                        loading ? (
                            <CircularProgress
                                size={16}
                                color="inherit"
                            />
                        ) : (
                            <UploadIcon />
                        )
                    }
                    disabled={loading}
                    sx={{ bgcolor: "background.darkGreen" }}
                >
                    {loading ? "Processando..." : label}
                </Button>
            </label>
        </>
    );
};

export default ImportButton;
