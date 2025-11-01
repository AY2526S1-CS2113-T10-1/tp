package finsight.storage.exceptions;

public class AmountPersistCorruptedException extends Exception {
    public AmountPersistCorruptedException(String amount, String type) {
        super("This " + type + " record contains the corrupted amount value [" + amount + "]. " +
                "Amount should contain ONLY positive numbers.\n" +
                "Please rectify the data in " + type + ".txt file and restart the program.");
    }
}
