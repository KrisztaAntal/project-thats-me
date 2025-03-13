import {useEffect, useState} from "react";
import {ZodSchema} from "zod";

export function useFormValidator<T>(schema: ZodSchema<T>,
                                    data: T,
                                    touchedFields: Record<string, boolean>,
                                    debounceTime: number = 300
) {
    const [errors, setErrors] = useState<Record<string, string>>({});

    useEffect(() => {
        const validationProcess = setTimeout(() => {
            const result = schema.safeParse(data);
            const newErrors: Record<string, string> = {};
            if (!result.success) {
                result.error.errors.forEach((err) => {
                    const fieldName = err.path.length ? err.path[0] : "unknown";
                    if (touchedFields[fieldName]) {
                        newErrors[fieldName] = err.message;
                    }
                })
            }
            setErrors(prev => (JSON.stringify(prev) !== JSON.stringify(newErrors) ? newErrors : prev));
        }, debounceTime)

        return () => clearTimeout(validationProcess);
    }, [schema, data, touchedFields, debounceTime]);
    return errors;
}
