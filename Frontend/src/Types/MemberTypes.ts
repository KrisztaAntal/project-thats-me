import {ChangeEvent} from "react";
import {z} from "zod";
import {loginSchema, newMemberSchema} from "../Schemas/MemberSchemas";

type FormFieldKey =
    "username"
    | "firstName"
    | "lastName"
    | "password"
    | "confirmPassword"
    | "email"
    | "birthDate"
    | "usernameOrEmail";
type labelValue =
    "Username"
    | "First Name"
    | "Last Name"
    | "Password"
    | "Confirm Password"
    | "Email"
    | "Birth Date"
    | "Username or Email";
type InputType = "text" | "password" | "email" | "date";
type Placeholder =
    "johnDoe"
    | "John"
    | "Doe"
    | "J0hnDoe@2025"
    | "johnDoe@gmail.com"
    | "2002-05-13"
    | "johnDoe or johnDoe@gmail.com";

export interface CustomFormFieldProps {
    name: FormFieldKey;
    inputId: FormFieldKey;
    labelValue: labelValue;
    inputType: InputType;
    inputValue: string;
    onChange: (e: ChangeEvent<HTMLInputElement>) => void;
    placeholder?: Placeholder;
    onShowPasswordClick?: () => void;
    error: string | boolean;
}

export interface JwtResponse {
    token: string;
    username: string;
    roles: Set<string>;
}

export interface AuthContextType {
    login: (credentials: LoginCredentials) => Promise<void>;
}


export type NewMember = z.infer<typeof newMemberSchema>;

export type LoginCredentials = z.infer<typeof loginSchema>;