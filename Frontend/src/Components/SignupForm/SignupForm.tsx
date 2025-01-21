import { FormEvent, useState } from "react";
import CustomFormField from "./CustomFormField";
import { NewMember } from "../../Types/MemberTypes";
import { useNavigate } from "react-router-dom";

async function createNewMember(newMember: NewMember) {
  const response = await fetch('/api/signup', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(newMember),
  });

  return await response.json();
}

function SignupForm() {
  const [username, setUsername] = useState<string>("");
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [showPassword, setShowPassword] = useState<boolean>(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState<boolean>(false);
  const [email, setEmail] = useState<string>("");
  const [birthDate, setBirthDate] = useState<string>("");
  const navigate = useNavigate();

  async function handleCreateMember(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    const newMember = { username, firstName, lastName, password, email, birthDate };
    await createNewMember(newMember);
    navigate("/signin");
  }

  return (
    <form className="flex flex-col gap-3 items-center" onSubmit={handleCreateMember}>
      <CustomFormField
        inputId="username"
        labelValue="Username"
        inputType="text"
        inputValue={username}
        onChange={(e) => setUsername(e.target.value)}
        placeholder="johnDoe">
      </CustomFormField>
      <CustomFormField
        inputId="firstname"
        labelValue="First Name"
        inputType="text"
        inputValue={firstName}
        onChange={(e) => setFirstName(e.target.value)}
        placeholder="John">
      </CustomFormField>
      <CustomFormField
        inputId="lastname"
        labelValue="Last Name"
        inputType="text"
        inputValue={lastName}
        onChange={(e) => setLastName(e.target.value)}
        placeholder="Doe">
      </CustomFormField>
      <CustomFormField
        inputId="password"
        labelValue="Password"
        inputType={showPassword ? "text" : "password"}
        inputValue={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="J0hnDoe@2025"
        onShowPasswordClick={() => setShowPassword((prev) => !prev)}>
      </CustomFormField>
      <CustomFormField
        inputId="confirmPassword"
        labelValue="Confirm Password"
        inputType={showConfirmPassword ? "text" : "password"}
        inputValue={confirmPassword}
        onChange={(e) => setConfirmPassword(e.target.value)}
        placeholder="J0hnDoe@2025"
        onShowPasswordClick={() => setShowConfirmPassword((prev) => !prev)}>
      </CustomFormField>
      <CustomFormField
        inputId="email"
        labelValue="Email"
        inputType="email"
        inputValue={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="johnDoe@gmail.com">
      </CustomFormField>
      <CustomFormField
        inputId="birthdate"
        labelValue="Birth Date"
        inputType="date"
        inputValue={birthDate}
        onChange={(e) => setBirthDate(e.target.value)}>
      </CustomFormField>
      <button className="bg-[#76ABAE] p-2">Continue</button>
    </form>
  )
}

export default SignupForm;