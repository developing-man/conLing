package cn.tedu.store.controller.ex;

/**
 * 文件上传相关异常的基类
 */
public class FileUploadException extends RuntimeException {
    private static final long serialVersionUID = -3921088080416592618L;

    public FileUploadException() {
        super();
    }

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadException(Throwable cause) {
        super(cause);
    }

    public FileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
