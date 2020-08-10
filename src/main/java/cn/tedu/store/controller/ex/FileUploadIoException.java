package cn.tedu.store.controller.ex;


/**
 * 上传文件读写异常
 */

public class FileUploadIoException extends FileUploadException {
    private static final long serialVersionUID = 7741594410378645181L;

    public FileUploadIoException() {
    }

    public FileUploadIoException(String message) {
        super(message);
    }

    public FileUploadIoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadIoException(Throwable cause) {
        super(cause);
    }

    public FileUploadIoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
