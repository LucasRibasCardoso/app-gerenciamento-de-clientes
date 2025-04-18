import { useState, useEffect } from "react";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";

interface DecodedToken {
    sub: string;
    role: string;
    iat: number;
    exp: number;
}

export function useAuth() {
    const [isAdmin, setIsAdmin] = useState(false);
    const [isManager, setIsManager] = useState(false);
    const navigate = useNavigate();

    const checkAuth = () => {
        const token = localStorage.getItem("token");
        if (token) {
            try {
                const decoded: DecodedToken = jwtDecode(token);

                // Verifica se o token expirou
                if (decoded.exp * 1000 < Date.now()) {
                    localStorage.removeItem("token");
                    setIsAdmin(false);
                    setIsManager(false);
                    navigate("/login");
                    return;
                }
                setIsAdmin(decoded.role === "ROLE_ADMIN");
                setIsManager(decoded.role === "ROLE_MANAGER");
            } catch (error) {
                console.error("Invalid token:", error);
                setIsAdmin(false);
                setIsManager(false);
            }
        } else {
            setIsAdmin(false);
            setIsManager(false);
        }
    };

    useEffect(() => {
        // Verifica inicial
        checkAuth();

        // Adiciona listeners para eventos de storage e navegação
        window.addEventListener("storage", checkAuth);
        window.addEventListener("popstate", checkAuth);

        return () => {
            // Remove os listeners quando o componente é desmontado
            window.removeEventListener("storage", checkAuth);
            window.removeEventListener("popstate", checkAuth);
        };
    }, []);

    return { isAdmin, isManager };
}
