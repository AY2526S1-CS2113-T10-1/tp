package finsight.storage;

import finsight.investment.Investment;
import finsight.investment.exceptions.AddInvestmentDateOutOfBoundsException;
import finsight.investment.exceptions.AddInvestmentWrongNumberFormatException;

import java.nio.file.Path;

public class InvestDataManager extends DataManager<Investment, Exception> {
    private final Path dataPath;

    private InvestDataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    public InvestDataManager(String fileName) {
        this(Path.of(fileName));
    }

    protected Path dataFilePath() {
        return dataPath;
    }

    protected String formatRecord(Investment investment) {
        String description = sanitize(investment.getDescription());
        String investAmount = investment.getInvestmentAmount().toString();
        String dayOfInvest = Integer.toString(investment.getInvestmentDateOfMonth());
        return String.join("|", description, investAmount, dayOfInvest);
    }

    protected Investment parseRecord(String line)
            throws AddInvestmentWrongNumberFormatException, AddInvestmentDateOutOfBoundsException {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) {
            return null;
        }
        String description = sanitize(parts[0]);
        String investAmount = parts[1];
        String dayOfInvest = parts[2];
        return new Investment(description, investAmount, dayOfInvest);
    }
}
