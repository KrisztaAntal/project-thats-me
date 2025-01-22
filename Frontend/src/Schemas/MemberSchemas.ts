import { z } from "zod";

export const newMemberSchema = z.object({
  username: z.string()
    .nonempty("Username is required.")
    .regex(/^[\p{L}0-9_\-.+]+$/u, "Username can only contain letters, digits, underscores (_), hyphens (-), periods (.), and plus signs (+)."),
  firstName: z.string()
    .nonempty("First name is required.")
    .regex(/^[\p{L}0-9 _\-.+]+$/u, "First name can only contain letters, digits, underscores (_), hyphens (-), periods (.), and plus signs (+)."),
  lastName: z.string()
    .nonempty("Last name is required.")
    .regex(/^[\p{L}0-9 _\-.+]+$/u, "Last name can only contain letters, digits, underscores (_), hyphens (-), periods (.), and plus signs (+)."),
  password: z.string()
    .nonempty("Password cannot be blank.")
    .min(8, "Password must be at least 8 characters long.")
    .max(20, "Password must not exceed 20 characters."),
  confirmPassword: z.string()
    .nonempty("Confirm password cannot be blank.")
    .min(8, "Confirm password must be at least 8 characters long.")
    .max(20, "Confirm password must not exceed 20 characters."),
  email: z.string().email("Invalid email address."),
  birthDate: z.string()
    .nonempty("Birth date is required.")
    .regex(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/, "Birth date must be in format YYYY-MM-DD.")
    .refine((date) => {
      const birthDate = new Date(date);
      const currentDate = new Date();
      const age = currentDate.getFullYear() - birthDate.getFullYear();
      const isBirthdayPassed = (currentDate.getMonth() > birthDate.getMonth()) ||
        (currentDate.getMonth() === birthDate.getMonth() &&
          currentDate.getDate() >= birthDate.getDate())
      return age > 14 || (age === 14 && isBirthdayPassed);
    }, { message: "User must be at least 14 years old." }),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords do not match.",
  path: ["confirmPassword"],
});