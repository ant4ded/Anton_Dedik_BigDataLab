package by.epam.data.analysis.crime.controller.command;

public class CommandRuntimeException extends RuntimeException {
    public CommandRuntimeException() {
        super();
    }

    public CommandRuntimeException(String message) {
        super(message);
    }

    public CommandRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandRuntimeException(Throwable cause) {
        super(cause);
    }
}
