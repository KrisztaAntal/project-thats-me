import {createContext, ReactNode} from "react";
import {AuthContextType, JwtResponse, LoginCredentials} from "../Types/MemberTypes.ts";

export const AuthContext = createContext<AuthContextType | null>(null);

const AuthProvider = ({children}: { children: ReactNode }) => {

    async function login(credentials: LoginCredentials) {
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
    }

    function logout(){
        localStorage.removeItem("token");
    }


    return (
        <AuthContext.Provider value={{login, logout}}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider;