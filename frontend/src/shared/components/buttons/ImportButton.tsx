import { useState } from "react";
import { Button, CircularProgress } from "@mui/material";
import UploadIcon from "@mui/icons-material/Upload";
import { read, utils } from "xlsx";
import { usePopUp } from "../../context/PopUpContext";

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
                const data = e.target?.result;
                const workbook = read(data, {
                    type: "array",
                    cellDates: true,
                    dateNF: "dd/mm/yyyy",
                });

                const firstSheetName = workbook.SheetNames[0];
                if (!firstSheetName) {
                    showMessage("Arquivo sem planilhas válidas", "error");
                    setLoading(false);
                    return;
                }

                const worksheet = workbook.Sheets[firstSheetName];

                // Converter para JSON com formatação de datas
                const jsonData = utils.sheet_to_json(worksheet, {
                    raw: false, // Não retorna valores brutos
                    dateNF: "dd/mm/yyyy", // Força a formatação de datas
                });

                if (jsonData.length === 0) {
                    showMessage("O arquivo não contém dados válidos", "error");
                    setLoading(false);
                    return;
                }

                // Validar os dados, se um validador for fornecido
                if (validator) {
                    const validation = validator(jsonData);
                    if (!validation.valid) {
                        showMessage(
                            `Erro na validação: ${validation.errors.join(", ")}`,
                            "error"
                        );
                        setLoading(false);
                        return;
                    }
                }

                // Retornar os dados processados
                onImportComplete(jsonData);
                showMessage("Importação realizada com sucesso!", "success");
            } catch (error) {
                console.error("Erro ao processar arquivo:", error);
                showMessage("Erro ao processar o arquivo. Verifique o formato.", "error");
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
