package cn.tedu.store.controller;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.ExcelObject;
import cn.tedu.store.util.JsonResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 控制用户信息的控制器
 */
@RequestMapping("users")
@RestController
public class UserController extends BaseController {
    @Resource
    private IUserService userService;

    @RequestMapping("re")
    public JsonResult<Void> reg(User user) {
        //执行注册
        userService.reg(user);
        //响应结果
        return new JsonResult<>(OK);
    }

    @RequestMapping("logi")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        //执行登录
        User data = userService.login(username, password);
        //登录成功,将uid和username存入到session中
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        //响应结果
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        //获取session的值
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        //执行修改
        userService.changePassword(uid, username, oldPassword, newPassword);
        //响应结果
        return new JsonResult<>(OK);
    }

    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        User data = userService.getByUid(uid);
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("change_info")
    public JsonResult<User> changeInfo(User user, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }

    /**
     * 上传头像文件大小的上限
     */
    public static final int AVATAR_MAX_SIZE = 500 * 1024;
    /**
     * 允许上传的头像的文件类型
     */
    public static final List<String> AVATAR_TYPES = new ArrayList<String>();

    /**
     * 初始化允许上传的头像的文件类型
     */
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }

    @RequestMapping("change_avatar")
    public JsonResult<String> changeInfo(@RequestParam("file") MultipartFile file, HttpSession session) {
        //判断文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("上传头像不能为空!");
        }
        //判断文件大小是否超过上限
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("上传头像大小超过了" + (AVATAR_MAX_SIZE / 1024) + "kb!");
        }

        // 判断上传的文件类型是否超出限制
        String contentType = file.getContentType();
        if (!AVATAR_TYPES.contains(contentType)) {
            // 是：抛出异常
            throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：\n" + AVATAR_TYPES);
        }

        //文件保存的文件夹位置
        String parent = session.getServletContext().getRealPath("upload");
        //创建该文件夹
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //文件的后缀
        String suffix = "";
        //获取原文件名
        String originalFilename = file.getOriginalFilename();
        //截取后缀
        int beginIndex = originalFilename.lastIndexOf(".");
        // 当文件名没有"."或是在Linux下以"."开头的隐藏文件时,就不截取
        if (beginIndex > 0) {
            suffix = originalFilename.substring(beginIndex);
        }

        //头像文件的文件名
        String fileName = UUID.randomUUID().toString() + suffix;
        //新建文件
        File dest = new File(dir, fileName);
        System.out.println(dest.getName());
        try {
            //保存文件
            file.transferTo(dest);
        } catch (IllegalStateException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        //在session中获取uid
        Integer uid = getUidFromSession(session);
        //在session中获取用户名
        String username = getUsernameFromSession(session);
        // 头像路径
        String avatar = "/upload/" + fileName;
        //更换头像的路径
        userService.changeAvatar(uid, username, avatar);


        //返回保存头像的路径
        return new JsonResult<>(OK, avatar);
    }


    @RequestMapping("regin")
    public void download(HttpServletResponse response) throws Exception {
        System.out.println(111);
        //临时生成测试数据
        String fileName = "导出excel例子.xls";
        String headTitle = "这是头标题";
        int colunmNum = 10;

        List<String> headTitleList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            headTitleList.add("第" + (i + 1) + "列标题");
        }
        List<List<String>> dataList = new ArrayList<List<String>>();
        for (int i = 0; i < 5; i++) {
            List<String> datas = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                datas.add("第" + (i + 1) + "行第" + (j + 1) + "列");
            }
            dataList.add(datas);
        }
        //1-创建一个HSSFWorkbook
        ExcelObject excel = new ExcelObject("实验数据");
        //2-写入头标题
        excel.createHeadTile(colunmNum, headTitle);//头标默认写在第一行
        //3-写入行标题
        excel.createRowTitle(headTitleList, 1);
        //4-写入具体数据
        excel.createDataByRow(2, dataList);//因为没有行标题，所以从第二行开始
        //5-生成excel文件
        excel.buildExcelFile(fileName);
        //6-浏览器下载excel
        excel.buildExcelDocument(fileName, response);
    }


}
