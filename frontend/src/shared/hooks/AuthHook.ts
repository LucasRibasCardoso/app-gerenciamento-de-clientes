import { useState, useEffect } from "react";
import { jwtDecode } from "jwt-decode";

interface DecodedToken {
  role: string;
}

export function useAuth() {
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decoded: DecodedToken = jwtDecode(token);
        setIsAdmin(decoded.role === "ADMIN");
      } 
      catch (error) {
        console.error("Invalid token", error);
        setIsAdmin(false);
      }
    }
  }, []);

  return { isAdmin };
}