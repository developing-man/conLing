package cn.tedu.store.controller.ex;


/**
 * 上传文件状态异常
 */
public class FileStateException extends FileUploadException {
    private static final long serialVersionUID = -8640003773675166181L;

    public FileStateException() {
    }

    public FileStateException(String message) {
        super(message);
    }

    public FileStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStateException(Throwable cause) {
        super(cause);
    }

    public FileStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
