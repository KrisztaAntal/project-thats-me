import { ChangeEvent } from "react";

type InputId = "username" | "firstname" | "lastname" | "password" | "confirmPassword" | "email" | "birthdate";
type labelValue = "Username" | "First Name" | "Last Name" | "Password" | "Confirm Password" | "Email" | "Birth Date";
type InputType = "text" | "password" | "email" | "date";
type Placeholder = "johnDoe" | "John" | "Doe" | "J0hnDoe@2025" | "johnDoe@gmail.com" | "2002-05-13";

export interface CustomFormFieldProps {
  inputId: InputId;
  labelValue: labelValue;
  inputType: InputType;
  inputValue: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
  placeholder?: Placeholder;
  onShowPasswordClick?: () => void;
}

export interface NewMember {
  username: string;
  firstName: string;
  lastName: string;
  password: string;
  email: string;
  birthDate: string;
}