import {
	Box,
	Typography,
	Paper,
	Card,
	CardContent,
	Divider,
	CircularProgress,
} from "@mui/material";
import { PieChart } from "@mui/x-charts/PieChart";
import Layout from "../../shared/layouts/Layout";
import { useDashboardData } from "../../shared/hooks/DashboardHook";
import { ClientsTable } from "../../shared/components/tables";
import { useState } from "react";

export default function Dashboard() {
	document.title = "Dashboard - Sistema de Gestão de Clientes";
	const { data, isLoading } = useDashboardData();
	const [_selectedClient, setSelectedClient] = useState<string | null>(null);

	// Dados para o gráfico de passaporte
	const passportStatusData = data
		? [
				{ id: 0, value: data.clientsWithPassport, label: "Com Passaporte" },
				{ id: 1, value: data.clientsWithoutPassport, label: "Sem Passaporte" },
		  ]
		: [];

	const clientsNeedingRenewal = data?.clientsThatNeedToRenewPassport || [];

	return (
		<Layout>
			{isLoading ? (
				<Box
					sx={{
						display: "flex",
						justifyContent: "center",
						alignItems: "center",
						height: "70vh",
					}}
				>
					<CircularProgress />
				</Box>
			) : (
				<Box sx={{ padding: 2 }}>
					<Box
						sx={{
							display: "flex",
							gap: 2,
							flexDirection: "column",
						}}
					>
						{/* KPI Cards */}
						<Box
							sx={{
								gap: 3,
								display: "flex",
								justifyContent: "space-between",
							}}
						>
							<Card elevation={3} sx={{ flex: 1 }}>
								<CardContent>
									<Typography color="textSecondary" gutterBottom variant="h6">
										Total de Clientes
									</Typography>
									<Typography variant="h5">{data?.totalClients || 0}</Typography>
								</CardContent>
							</Card>

							<Card elevation={3} sx={{ flex: 1 }}>
								<CardContent>
									<Typography color="textSecondary" gutterBottom variant="h6">
										Novos Clientes (últimos 30 dias)
									</Typography>
									<Typography variant="h5">{data?.newClientsLast30Days || 0}</Typography>
								</CardContent>
							</Card>

							<Card elevation={3} sx={{ flex: 1 }}>
								<CardContent>
									<Typography color="textSecondary" gutterBottom variant="h6">
										Clientes com Passaporte para Renovar
									</Typography>
									<Typography variant="h5">
										{clientsNeedingRenewal.length || 0}
									</Typography>
								</CardContent>
							</Card>
						</Box>

						{/* Charts Section */}
						<Box>
							<Paper elevation={3} sx={{ p: 2, height: 300 }}>
								<Typography variant="h6" gutterBottom align="center">
									Passaportes
								</Typography>
								{data && (
									<PieChart
										series={[
											{
												data: passportStatusData,
												innerRadius: 30,
												paddingAngle: 2,
												cornerRadius: 5,
												startAngle: -90,
												endAngle: 270,
											},
										]}
										width={300}
										height={200}
									/>
								)}
							</Paper>
						</Box>
					</Box>

					{/* Expiring Passports Section */}
					<Paper elevation={3} sx={{ p: 3, mt: 2 }}>
						<Typography variant="h6" gutterBottom>
							Clientes que precisam renovar passaporte em até 6 meses
						</Typography>
						<Divider sx={{ mb: 2 }} />

						{clientsNeedingRenewal.length > 0 ? (
							<ClientsTable
								clients={clientsNeedingRenewal}
								pageSize={10}
								currentPage={0}
								totalElements={clientsNeedingRenewal.length}
								onSelectClient={setSelectedClient}
								onPageChange={() => {}}
								onPageSizeChange={() => {}}
								showPagination={false}
							/>
						) : (
							<Typography sx={{ py: 4, textAlign: "center" }} color="text.secondary">
								Nenhum cliente com passaporte a vencer em breve.
							</Typography>
						)}
					</Paper>
				</Box>
			)}
		</Layout>
	);
}
