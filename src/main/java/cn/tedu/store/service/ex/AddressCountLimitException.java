package cn.tedu.store.service.ex;

/**
 * 用户添加地址数量超过上限异常
 */
public class AddressCountLimitException extends ServiceException {
    private static final long serialVersionUID = 212741039716856010L;

    public AddressCountLimitException() {
    }

    public AddressCountLimitException(String message) {
        super(message);
    }

    public AddressCountLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressCountLimitException(Throwable cause) {
        super(cause);
    }

    public AddressCountLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
