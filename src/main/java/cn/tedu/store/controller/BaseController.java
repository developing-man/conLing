package cn.tedu.store.controller;

import cn.tedu.store.aop.LogAspect;
import cn.tedu.store.controller.ex.*;
import cn.tedu.store.service.ex.*;
import cn.tedu.store.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;
public class BaseController {
    /**
     * 操作成功的状态码
     */
    public static final int OK = 2000;

    /**
     * 获取session里的uid
     *
     * @param session HttpSession对象
     * @return 当前登录对象的uid
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取session里的username
     *
     * @param session HttpSession对象
     * @return 当前登录对象的username
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> jr = new JsonResult<>(e);
        logger.error("localizaizedMessage : {}", e.getLocalizedMessage());
        logger.error("exception message : {}", e.getMessage());
        logger.error("exception cause : {}", e.getCause());
        //异常输出
        logger.error("exception toString and track space : {}", "\r\n" + e);
        logger.error(BaseController.errorTrackSpace(e));
        logger.error("---------------------------------------------");


        if (e instanceof UsernameDuplicateException) {
            //用户名重复,4XXX代表可以预知的错误
            jr.setState(4000);
        } else if (e instanceof UserNotFoundException) {
            jr.setState(4001);
        } else if (e instanceof PasswordNotMatchException) {
            jr.setState(4002);
        } else if (e instanceof AddressCountLimitException) {
            jr.setState(4003);
        } else if (e instanceof ProductNotFoundException) {
            jr.setState(4006);
        } else if (e instanceof CartNotFoundException) {
            jr.setState(4007);
        } else if (e instanceof InsertException) {
            //5XXX代表未知原因发生的错误
            jr.setState(5000);
        } else if (e instanceof UpdateException) {
            jr.setState(5001);
        } else if (e instanceof DeleteException) {
            jr.setState(5002);
        } else if (e instanceof FileEmptyException) {
            jr.setState(6000);
        } else if (e instanceof FileSizeException) {
            jr.setState(6001);
        } else if (e instanceof FileTypeException) {
            jr.setState(6002);
        } else if (e instanceof FileStateException) {
            jr.setState(6003);
        } else if (e instanceof FileUploadIoException) {
            jr.setState(6004);
        }

        return jr;
    }
    /**
     * 输出异常信息
     * @param e
     * @return
     */
    private static String errorTrackSpace(Throwable e) {
        StringBuffer sb = new StringBuffer();
        if (e != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                sb.append("\r\n\t").append(element);
            }
        }
        return sb.length() == 0 ? null : sb.toString();
    }


}
