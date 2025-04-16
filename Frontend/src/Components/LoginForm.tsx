import CustomFormField from "./SignupForm/CustomFormField.tsx";
import {FormEvent, useState} from "react";
import {LoginCredentials} from "../Types/MemberTypes.ts";
import {loginSchema} from "../Schemas/MemberSchemas.ts";
import {useFormValidator} from "./useFormValidator.tsx";
import {useAuth} from "../authProvider/useAuth.tsx";
import {useNavigate} from "react-router-dom";

function LoginForm() {
    const [loginCredentials, setLoginCredentials] = useState<LoginCredentials>({
        usernameOrEmail: "",
        password: ""
    });
    const [touchedFields, setTouchedFields] = useState<Record<string, boolean>>({});
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const errors = useFormValidator(loginSchema, loginCredentials, touchedFields);
    const {login} = useAuth();
    const navigate = useNavigate();

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
        <form className="flex flex-col gap-4 items-center" onSubmit={handleLogin}>
            <CustomFormField name={"usernameOrEmail"} inputId={"usernameOrEmail"} labelValue={"Username or Email"}
                             inputType={"text"} inputValue={loginCredentials.usernameOrEmail}
                             onChange={handleInputChange} error={touchedFields.usernameOrEmail && errors.usernameOrEmail}/>
            <CustomFormField name={"password"} inputId={"password"} labelValue={"Password"}
                             inputType={showPassword ? "text" : "password"}
                             inputValue={loginCredentials.password} onChange={handleInputChange}
                             onShowPasswordClick={() => setShowPassword((prev) => !prev)}
                             error={touchedFields.password && errors.password}/>
            <button className="btn bg-[#9EF01A] p-2 hover:bg-accent active:bg-success mt-6 ">Continue</button>
        </form>
    )
}

export default LoginForm;