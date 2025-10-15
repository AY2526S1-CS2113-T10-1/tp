package finsight.storage;

import finsight.income.Income;
import finsight.income.exceptions.AddIncomeCommandWrongFormatException;

import java.nio.file.Path;

public class IncomeDataManager extends DataManager<Income, AddIncomeCommandWrongFormatException> {
    private final Path dataPath;

    private IncomeDataManager(Path dataPath) {
        this.dataPath = dataPath;
    }

    public IncomeDataManager(String filename) {
        this(Path.of(filename));
    }

    protected Path dataFilePath() {
        return dataPath;
    }

    protected String formatRecord(Income income) {
        String description = sanitize(income.getDescription());
        String incomeAmount = Float.toString(income.getAmountEarned());
        return String.join("|", description, incomeAmount);
    }

    protected Income parseRecord(String line) throws AddIncomeCommandWrongFormatException {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 2) {
            return null;
        }
        String description = unsanitize(parts[0]);
        String incomeAmount = parts[1];
        return new Income(description, incomeAmount);
    }
}
