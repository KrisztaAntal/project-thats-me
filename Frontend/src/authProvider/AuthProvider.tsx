import {createContext, ReactNode, useEffect, useState} from "react";
import {AuthContextType, JwtResponse, LoginCredentials, Member} from "../Types/MemberTypes.ts";

export const AuthContext = createContext<AuthContextType | null>(null);

const getInitialState = () => {
    const currentMember = localStorage.getItem("currentMember");
    return currentMember ? JSON.parse(currentMember) : null
}

const AuthProvider = ({children}: { children: ReactNode }) => {
    const [member, setMember] = useState<Member | null>(getInitialState);


    const getMember = async () => {
        const token = localStorage.getItem("token");
        if (!token) return;
        try {
            const response = await fetch("/api/member/me", {
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

    useEffect(() => {
        localStorage.setItem("currentMember", JSON.stringify(member));
    }, [member])


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
        } catch (error) {
            console.error("Login error:", error);
            alert(error instanceof Error ? error.message : "Something went wrong");
        }
    }

    const logout = () => {
        localStorage.removeItem("token");
        setMember(null);
        localStorage.removeItem("currentMember");
    }


    return (
        <AuthContext.Provider value={{login, logout, member}}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider;