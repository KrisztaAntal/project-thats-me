import {createContext, ReactNode, useState} from "react";
import {AuthContextType, JwtResponse, LoginCredentials, Member} from "../Types/MemberTypes.ts";
import {useNavigate} from "react-router-dom";

export const AuthContext = createContext<AuthContextType | null>(null);

const AuthProvider = ({children}: { children: ReactNode }) => {
    const [member, setMember] = useState<Member | null>(null);
    const navigate = useNavigate();


    const getMember = async () => {
        const token = localStorage.getItem("token");
        if (!token) return;
        try {
            const response = await fetch("/api/user/me", {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                throw new Error("Failed to fetch user data");
            }

            const memberData: Member = await response.json();
            setMember(memberData);
        } catch (error) {
            console.error("Error fetching user:", error);
            logout();
        }
    }


    const login = async (credentials: LoginCredentials) => {
        try {
            const response = await fetch('/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(credentials),
            });

            if (!response.ok) {
                const errorData = await response.json();
                alert(errorData.message);
            }
            const jwtResponse: JwtResponse = await response.json();
            localStorage.setItem("token", jwtResponse.token);
            await getMember();
            navigate("/main");
        } catch (error) {
            console.error("Login error:", error);
            alert(error instanceof Error ? error.message : "Something went wrong");
        }
    }

    const logout = () => {
        localStorage.removeItem("token");
    }


    return (
        <AuthContext.Provider value={{login, logout, member}}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider;