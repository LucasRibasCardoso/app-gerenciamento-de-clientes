import { Controller, Control, FieldErrors, FieldValues, Path } from "react-hook-form";
import { TextField } from "@mui/material";

interface TextInputProps<T extends FieldValues> {
    name: Path<T>; // Usamos Path<T> para garantir que o name seja uma chave válida de T
    control: Control<T, any>;
    label: string;
    errors: FieldErrors<T>;
    [key: string]: any; // Para permitir outras props
}

const TextInput = <T extends FieldValues>({ name, control, label, errors, ...rest }: TextInputProps<T>) => {
    return (
        <Controller
            name={name} // Não é mais necessário converter para string
            control={control}
            render={({ field }) => (
                <TextField
                    {...field}
                    label={label}
                    variant="outlined"
                    fullWidth
                    error={!!errors[name]}
                    helperText={errors[name]?.message?.toString() || ""}
                    {...rest}
                />
            )}
        />
    );
};

export default TextInput;