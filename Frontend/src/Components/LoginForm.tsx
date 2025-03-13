import CustomFormField from "./SignupForm/CustomFormField.tsx";
import {FormEvent, useState} from "react";
import {LoginCredentials} from "../Types/MemberTypes.ts";
import {loginSchema} from "../Schemas/MemberSchemas.ts";
import {useNavigate} from "react-router-dom";
import {useFormValidator} from "./useFormValidator.tsx";

async function login(loginCredentials: LoginCredentials): Promise<string> {
    const response = await fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginCredentials),
    });

    if (!response.ok) {
        const errorData = await response.json();
        alert(errorData.message);
    }
    return await response.json();
}

function LoginForm() {
    const [loginCredentials, setLoginCredentials] = useState<LoginCredentials>({
        usernameOrEmail: "",
        password: ""
    });
    const [touchedFields, setTouchedFields] = useState<Record<string, boolean>>({});
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const navigate = useNavigate();
    const errors = useFormValidator(loginSchema, loginCredentials, touchedFields);


    async function handleLogin(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const result = loginSchema.safeParse(loginCredentials);
        if (result.success) {
            try {
                await login(loginCredentials);
                navigate("/main");
            } catch (error) {
                alert('There has been a problem with your fetch operation: ' + (error instanceof Error ? error.message : ""));
            }
        } else {
            alert("Unexpected error occurred. Please try again later.");
        }
    }


    function handleInputChange(e: React.ChangeEvent<HTMLInputElement>) {
        const {name, value} = e.target;
        setLoginCredentials((prev) => ({...prev, [name]: value}));

        setTouchedFields((prev) => ({...prev, [name]: true}));
    }

    return (
        <form onSubmit={handleLogin}>
            <CustomFormField name={"usernameOrEmail"} inputId={"usernameOrEmail"} labelValue={"Username or Email"}
                             inputType={"text"} inputValue={loginCredentials.usernameOrEmail}
                             onChange={handleInputChange} error={touchedFields.usernameOrEmail && errors.usernameOrEmail}/>
            <CustomFormField name={"password"} inputId={"password"} labelValue={"Password"}
                             inputType={showPassword ? "text" : "password"}
                             inputValue={loginCredentials.password} onChange={handleInputChange}
                             onShowPasswordClick={() => setShowPassword((prev) => !prev)}
                             error={touchedFields.password && errors.password}/>
            <button>Continue</button>
        </form>
    )
}

export default LoginForm;