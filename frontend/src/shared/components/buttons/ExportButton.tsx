import { Button } from "@mui/material";
import DownloadIcon from "@mui/icons-material/Download";
import { utils, write } from "xlsx";

interface ExportButtonProps {
    data: any[];
    fileName: string;
    label?: string;
}

const ExportButton: React.FC<ExportButtonProps> = ({
    data,
    fileName,
    label = "Exportar",
}) => {
    const exportToExcel = () => {
        const worksheet = utils.json_to_sheet(data);
        const workbook = utils.book_new();
        utils.book_append_sheet(workbook, worksheet, "Dados");

        // Gerar arquivo Excel (.xlsx)
        const excelBuffer = write(workbook, { bookType: "xlsx", type: "array" });

        // Criar um blob com os dados
        const blob = new Blob([excelBuffer], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });

        // Criar URL para download
        const url = URL.createObjectURL(blob);
        const link = document.createElement("a");
        link.href = url;
        link.download = `${fileName}.xlsx`;

        // Trigger download
        document.body.appendChild(link);
        link.click();

        // Cleanup
        document.body.removeChild(link);
        URL.revokeObjectURL(url);
    };

    return (
        <Button
            variant="contained"
            startIcon={<DownloadIcon />}
            onClick={exportToExcel}
            sx={{ bgcolor: "background.darkGreen" }}
        >
            {label}
        </Button>
    );
};

export default ExportButton;
