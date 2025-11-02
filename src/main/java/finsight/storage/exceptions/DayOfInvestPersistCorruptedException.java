package finsight.storage.exceptions;

public class DayOfInvestPersistCorruptedException extends RuntimeException {
    public DayOfInvestPersistCorruptedException(String dayOfInvest) {
        super("This investment record contains the corrupted day of investment [" + dayOfInvest + "]. " +
                "A valid day should be between 1 and 31 (inclusive).\n" +
                "Please rectify the data in invest.txt file and restart the program.");
    }
}
