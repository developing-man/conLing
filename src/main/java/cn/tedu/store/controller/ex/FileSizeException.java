package cn.tedu.store.controller.ex;


/**
 * 文件大小超出限制
 */
public class FileSizeException extends FileUploadException {
    private static final long serialVersionUID = 5302269116229851354L;

    public FileSizeException() {
    }

    public FileSizeException(String message) {
        super(message);
    }

    public FileSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSizeException(Throwable cause) {
        super(cause);
    }

    public FileSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
