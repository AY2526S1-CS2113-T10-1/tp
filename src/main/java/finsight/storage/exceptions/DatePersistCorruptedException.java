package finsight.storage.exceptions;

public class DatePersistCorruptedException extends RuntimeException {
    public DatePersistCorruptedException(String date) {
        super("The loan record contains the corrupted date [" + date + "]. " +
                "Date should be in <DD-MM-YYYY HH:mm> format.\n" +
                "Please rectify the loan.txt file and restart the program.");
    }
}
