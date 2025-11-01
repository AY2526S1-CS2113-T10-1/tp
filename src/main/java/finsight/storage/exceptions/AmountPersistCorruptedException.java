package finsight.storage.exceptions;

public class AmountPersistCorruptedException extends Exception {
    public AmountPersistCorruptedException(String amount) {
        super("This loan record contains the corrupted amount value [" + amount + "]. " +
                "Amount should contain ONLY positive numbers.\n" +
                "Please rectify the data in loan.txt file and restart the program.");
    }
}
