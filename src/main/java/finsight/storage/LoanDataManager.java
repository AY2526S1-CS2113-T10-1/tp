package finsight.storage;

import finsight.loan.Loan;
import finsight.loan.exceptions.AddLoanCommandWrongFormatException;

import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

public class LoanDataManager extends DataManager<Loan, AddLoanCommandWrongFormatException> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final Path dataPath;

    private LoanDataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    public LoanDataManager(String fileName) {
        this(Path.of(fileName));
    }

    protected Path dataFilePath() {
        return dataPath;
    }

    protected String formatRecord(Loan loan) {
        String repaid = loan.isRepaid() ? "1" : "0";
        String description = sanitize(loan.getDescription());
        String loanAmount = loan.getAmountLoaned().toString();
        String returnBy = loan.getLoanReturnDate().format(FORMATTER);
        return String.join("|", repaid, description, loanAmount, returnBy);
    }

    protected Loan parseRecord(String line) throws AddLoanCommandWrongFormatException {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 4) {
            return null;
        }
        boolean isRepaid = parts[0].equals("1");
        String description = unsanitize(parts[1]);
        String loanAmount = parts[2];
        String returnBy = parts[3];
        Loan loan = new Loan(description, loanAmount, returnBy);
        if (isRepaid) {
            loan.setRepaid();
        } else {
            loan.setNotRepaid();
        }
        return loan;
    }
}
