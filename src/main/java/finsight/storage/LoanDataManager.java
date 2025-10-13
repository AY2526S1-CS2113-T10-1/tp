package finsight.storage;

import finsight.loan.Loan;
import finsight.loan.loanlist.LoanList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoanDataManager {
    private static final Path DEFAULT_PATH = Path.of("data", "loan.txt");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final Path dataPath;

    private LoanDataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    public LoanDataManager(String fileName) {
        this(Path.of(fileName));
    }

    private void ensureParentDir() throws IOException {
        Path parent = dataPath.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

    private void ensureFileExist() throws IOException {
        ensureParentDir();
        if (!Files.exists(dataPath)) {
            Files.createFile(dataPath);
        }
    }

    public ArrayList<Loan> load() throws IOException {
        ensureFileExist();
        List<String> lines = Files.readAllLines(dataPath, StandardCharsets.UTF_8);
        ArrayList<Loan> loans = new ArrayList<>(lines.size());
        for (String line : lines) {
            if (line == null || line.isEmpty()) {
                continue;
            }
            Loan loan = parseRecord(line);
            if (loan != null) {
                loans.add(loan);
            }
        }
        return loans;
    }

    public void writeToFile(List<Loan> loans) throws IOException {
        ensureParentDir();
        Path tmp = dataPath.resolveSibling(dataPath.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8)) {
            for (Loan loan : loans) {
                writer.write(formatRecord(loan));
                writer.newLine();
            }
        }
        Files.move(tmp, dataPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    public void appendToFile(Loan loan) throws IOException {
        ensureFileExist();
        try (BufferedWriter writer = Files.newBufferedWriter(dataPath, StandardCharsets.UTF_8,StandardOpenOption.APPEND)) {
            writer.write(formatRecord(loan));
            writer.newLine();
        }
    }

    private String formatRecord(Loan loan) {
        String repaid = loan.isRepaid() ? "1" : "0";
        String description = sanitize(loan.getDescription());
        String loanAmount = loan.getAmountLoaned().toString();
        String returnBy = loan.getLoanReturnDate().format(FORMATTER);
        return String.join("|", repaid, description, loanAmount, returnBy);
    }

    private Loan parseRecord(String line) {
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

    private String sanitize(String line) {
        return line == null? "" : line.replace("|", "/");
    }

    private String unsanitize(String line) {
        return line;
    }
}
