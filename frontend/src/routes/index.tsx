import { Routes, Route, Navigate } from "react-router-dom";
import Home  from '../pages/home';
import Login from '../pages/login';

export const AppRoutes = () => {
    
    return (
        <Routes>
            <Route path="/Login" element={ <Login /> }/>
            <Route path="/home" element={ <Home /> }/>
            <Route path="*" element={<Navigate to="/home" />} />
        </Routes>
    );
}