import { Routes, Route, Navigate } from "react-router-dom";
import Home from "../pages/home/HomePage";
import Login from "../pages/login/LoginPage";
import Clients from "../pages/clients/ClientPage";
import UserPage from "../pages/users/UserPage";
import Dashboard from "../pages/dashboard/DashboardPage";

export const AppRoutes = () => {
	return (
		<Routes>
			<Route path="/Login" element={<Login />} />
			<Route path="/home" element={<Home />} />
			<Route path="/clientes" element={<Clients />} />
			<Route path="/usuarios" element={<UserPage />} />
			<Route path="*" element={<Navigate to="/login" />} />
			<Route path="/dashboard" element={<Dashboard />} />
		</Routes>
	);
};
