import { Routes, Route, Navigate } from "react-router-dom";
import Home  from "../pages/home/HomePage";
import Login from "../pages/login/LoginPage";
import Clients from "../pages/clients/ClientPage";
import FormSaveClient from "../pages/save-client/SaveClient";

export const AppRoutes = () => {
    
    return (
        <Routes>
            <Route path="/Login" element={ <Login /> }/>
            <Route path="/home" element={ <Home /> }/>
            <Route path="/clientes" element={ <Clients /> }/>
            <Route path="/cliente/:id?" element={ <FormSaveClient/> }/>
            <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
    );
}