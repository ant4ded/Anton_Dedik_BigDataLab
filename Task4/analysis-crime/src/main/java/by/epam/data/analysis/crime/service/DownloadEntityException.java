package by.epam.data.analysis.crime.service;

public class DownloadEntityException extends RuntimeException {

    public DownloadEntityException() {
        super();
    }

    public DownloadEntityException(String message) {
        super(message);
    }

    public DownloadEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloadEntityException(Throwable cause) {
        super(cause);
    }

    protected DownloadEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
