import { Button } from "@mui/material";
import DownloadIcon from "@mui/icons-material/Download";
import { utils, write } from "xlsx";
import { useGetAllClients } from "../../hooks/ClientHook";
import { usePopUp } from "../../context/PopUpContext";

const ExportButton = () => {
	const { showMessage } = usePopUp();

	// Buscar todos os clientes para exportar
	const { data: clients } = useGetAllClients({
		size: 1000,
	});

	// Preparar dados para exportação
	const prepareClientsForExport = () => {
		if (clients?.data.length == 0) {
			showMessage("Não há nenhum cadastro para exportar", "info");
		}
		return (
			clients?.data.map((client) => ({
				ID: client.id,
				Nome: client.completeName,
				CPF: client.cpf,
				DataNascimento: client.birthDate,
				Telefone: client.phone || "",
				Email: client.email || "",
				Passaporte: client.passport?.number || "",
				Data_Emissão: client.passport?.emissionDate || "",
				Data_Vencimento: client.passport?.expirationDate || "",
				CEP: client.address?.zipCode || "",
				País: client.address?.country || "",
				Estado: client.address?.state || "",
				Cidade: client.address?.city || "",
				Bairro: client.address?.neighborhood || "",
				Rua: client.address?.street || "",
				Complemento: client.address?.complement || "",
				Número: client.address?.residentialNumber || "",
			})) || []
		);
	};

	const exportToExcel = () => {
		const worksheet = utils.json_to_sheet(prepareClientsForExport());
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
		link.download = "clientes-exportados.xlsx";

		// Trigger download
		document.body.appendChild(link);
		link.click();

		// Cleanup
		document.body.removeChild(link);
		URL.revokeObjectURL(url);
	};

	return (
		<Button
			variant="outlined"
			startIcon={<DownloadIcon />}
			onClick={exportToExcel}
		>
			EXPORTAR
		</Button>
	);
};

export default ExportButton;
