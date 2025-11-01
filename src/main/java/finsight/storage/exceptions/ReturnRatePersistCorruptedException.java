package finsight.storage.exceptions;

public class ReturnRatePersistCorruptedException extends RuntimeException {
    public ReturnRatePersistCorruptedException(String rateOfReturn) {
        super("This investment record contains the corrupted rate of return value [" + rateOfReturn + "]. " +
                "Rate of return should contain ONLY positive numbers.\n" +
                "Please rectify the data in invest.txt file and restart the program.");
    }
}
