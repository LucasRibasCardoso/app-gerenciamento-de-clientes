import { Button } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";

type SaveButtonProps = {
    onClick?: () => void;
};

const SaveButton = ({ onClick }: SaveButtonProps) => (
    <Button
        type="submit"
        variant="contained"
        fullWidth
        startIcon={<SaveIcon />}
        onClick={onClick}
    >
        Salvar
    </Button>
);

export default SaveButton;