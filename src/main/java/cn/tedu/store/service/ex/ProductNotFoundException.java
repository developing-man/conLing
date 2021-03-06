package cn.tedu.store.service.ex;

/**
 * 用户数据不存在异常
 */
public class ProductNotFoundException extends ServiceException {

    private static final long serialVersionUID = -2111882581918102362L;

    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProductNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
