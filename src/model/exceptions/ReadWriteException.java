package model.exceptions;

public class ReadWriteException extends Exception {
    public ReadWriteException(String errorMsg) {
        super(errorMsg);
    }
}
