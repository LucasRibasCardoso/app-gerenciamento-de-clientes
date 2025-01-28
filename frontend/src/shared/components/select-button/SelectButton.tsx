import * as React from "react";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";

interface SelectProps {
  label: string; 
  value: string;
  options: { value: string; label: string }[];
  onChange: (value: string) => void; // Callback para lidar com a mudança
  minWidth?: string | number; 
}

const SelectButton: React.FC<SelectProps> = ({
  label,
  value,
  options,
  onChange,
  minWidth = "110px",
}) => {
  const handleChange = (event: SelectChangeEvent) => {
    const selectedValue = event.target.value;
    onChange(selectedValue);
  };

  return (
    <FormControl
      sx={{
        minWidth,
        "& .MuiOutlinedInput-root": {
          height: "45px",
          "& .MuiSelect-select": {
            display: "flex",
            alignItems: "center",
          },
          "& fieldset": {
            borderColor: "#404040",
          },
        },
        "& .MuiInputLabel-root": {
          top: "-5px",
        },
      }}
    >
      <InputLabel>{label}</InputLabel>
      <Select 
        value={value} 
        label={label} 
        onChange={handleChange}
      >
        {options.map((option) => (
          <MenuItem key={option.value} value={option.value}>
            {option.label}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
};

export default SelectButton;
