import { Controller, Control, FieldErrors, FieldValues, Path } from "react-hook-form";
import { TextField } from "@mui/material";
import InputMask from "react-input-mask";

interface MaskedInputProps<T extends FieldValues> {
    name: Path<T>; 
    control: Control<T, any>;
    label: string;
    mask: string;
    errors: FieldErrors<T>;
    isEditing?: boolean;
    [key: string]: any; 
}

const MaskedInput = <T extends FieldValues>({ name, control, label, mask, errors, isEditing, ...rest }: MaskedInputProps<T>) => {
    return (
        <Controller
            name={name} 
            control={control}
            render={({ field }) => (
                <InputMask
                    mask={mask}
                    value={field.value || ""}
                    onChange={field.onChange}
                    disabled={isEditing && (name as string) === "cpf"}
                >
                    {(inputProps: any) => (
                        <TextField
                            {...inputProps}
                            label={label}
                            variant="outlined"
                            fullWidth
                            error={!!errors[name]}
                            helperText={errors[name]?.message?.toString() || ""}
                            {...rest}
                        />
                    )}
                </InputMask>
            )}
        />
    );
};

export default MaskedInput;