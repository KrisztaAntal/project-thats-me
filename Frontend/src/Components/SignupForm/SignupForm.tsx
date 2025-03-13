import {FormEvent, useState} from "react";
import CustomFormField from "./CustomFormField";
import {NewMember} from "../../Types/MemberTypes";
import {useNavigate} from "react-router-dom";
import {newMemberSchema} from "../../Schemas/MemberSchemas";
import {useFormValidator} from "../useFormValidator.tsx";

async function createNewMember(newMember: NewMember): Promise<string> {
    const response = await fetch('/api/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newMember),
    });

    if (!response.ok) {
        const errorData = await response.json();
        alert(errorData.message);
    }
    return await response.json();
}

function SignupForm() {
    const [newMember, setNewMember] = useState<NewMember>({
        username: "",
        password: "",
        confirmPassword: "",
        email: "",
        birthDate: ""
    });
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState<boolean>(false);
    const [touchedFields, setTouchedFields] = useState<Record<string, boolean>>({});
    const navigate = useNavigate();

    const errors = useFormValidator(newMemberSchema, newMember, touchedFields);


    async function handleCreateMember(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const result = newMemberSchema.safeParse(newMember);
        if (result.success) {
            try {
                await createNewMember(newMember);
                navigate("/login");
            } catch (error) {
                alert('There has been a problem with your fetch operation: ' + (error instanceof Error ? error.message : ""));
            }
        } else {
            alert("Unexpected error occurred. Please try again later.");
        }
    }

    function handleInputChange(e: React.ChangeEvent<HTMLInputElement>) {
        const {name, value} = e.target;
        setNewMember((prev) => ({...prev, [name]: value}));

        setTouchedFields((prev) => ({...prev, [name]: true}));
    }

    return (
        <form className="flex flex-col gap-4 items-center" onSubmit={handleCreateMember}>
            <CustomFormField
                name="username"
                inputId="username"
                labelValue="Username"
                inputType="text"
                inputValue={newMember.username}
                onChange={handleInputChange}
                placeholder="johnDoe"
                error={touchedFields.username && errors.username}>
            </CustomFormField>
            <CustomFormField
                name="password"
                inputId="password"
                labelValue="Password"
                inputType={showPassword ? "text" : "password"}
                inputValue={newMember.password}
                onChange={handleInputChange}
                placeholder="J0hnDoe@2025"
                onShowPasswordClick={() => setShowPassword((prev) => !prev)}
                error={touchedFields.password && errors.password}>
            </CustomFormField>
            <CustomFormField
                name="confirmPassword"
                inputId="confirmPassword"
                labelValue="Confirm Password"
                inputType={showConfirmPassword ? "text" : "password"}
                inputValue={newMember.confirmPassword}
                onChange={handleInputChange}
                placeholder="J0hnDoe@2025"
                onShowPasswordClick={() => setShowConfirmPassword((prev) => !prev)}
                error={touchedFields.confirmPassword && errors.confirmPassword}>
            </CustomFormField>
            <CustomFormField
                name="email"
                inputId="email"
                labelValue="Email"
                inputType="email"
                inputValue={newMember.email}
                onChange={handleInputChange}
                placeholder="johnDoe@gmail.com"
                error={touchedFields.email && errors.email}>
            </CustomFormField>
            <CustomFormField
                name="birthDate"
                inputId="birthDate"
                labelValue="Birth Date"
                inputType="date"
                inputValue={newMember.birthDate}
                onChange={handleInputChange}
                error={touchedFields.birthDate && errors.birthDate}>
            </CustomFormField>
            <p className="text-white">Already have an account?
                <button className="btn btn-link text-white px-2 pb-1 " onClick={() => navigate("/login")}>Log in</button></p>

            <button className="btn bg-[#9EF01A] p-2 hover:bg-accent active:bg-success">Continue</button>
        </form>
    )
}

export default SignupForm;