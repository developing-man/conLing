package cn.tedu.store.controller.ex;

/**
 * 上传文件类型异常
 */
public class FileTypeException extends FileUploadException {
    private static final long serialVersionUID = -4668299874374727452L;

    public FileTypeException() {
    }

    public FileTypeException(String message) {
        super(message);
    }

    public FileTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTypeException(Throwable cause) {
        super(cause);
    }

    public FileTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
