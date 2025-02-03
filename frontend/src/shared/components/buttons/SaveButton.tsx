import { Button } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";

type SaveButtonProps = {
    onClick?: () => void;
    disabled?: boolean;
};

const SaveButton = ({ onClick, disabled }: SaveButtonProps) => (
    <Button
        type="submit"
        variant="contained"
        fullWidth
        startIcon={<SaveIcon />}
        onClick={onClick}
        disabled={disabled}
    >
        Salvar
    </Button>
);

export default SaveButton;