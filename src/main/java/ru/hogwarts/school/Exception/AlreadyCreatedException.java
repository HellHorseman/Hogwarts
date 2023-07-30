package ru.hogwarts.school.Exception;

public class AlreadyCreatedException extends RuntimeException{
    public AlreadyCreatedException() {
    }

    public AlreadyCreatedException(String message) {
        super(message);
    }

    public AlreadyCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyCreatedException(Throwable cause) {
        super(cause);
    }

    public AlreadyCreatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
