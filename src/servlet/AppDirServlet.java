package servlet;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取初始化配置
 * 扫描初始化目录
 */
@WebServlet("/AppDirServlet")
public class AppDirServlet extends HttpServlet {
    String appRootDirPath = "";

    @Override
    public void init() throws ServletException {
        appRootDirPath = PropertyUtils.getPropertyByName("application", "appRootDirPath");
        if (appRootDirPath == null || appRootDirPath.length() == 0) {
            appRootDirPath = this.getServletConfig().getServletContext().getRealPath("/") + Constant.TEST_APP_DIR_NAME;
        }
        File rootFile = new File(appRootDirPath);
        if (rootFile.isDirectory()) {
            mkdirOnlineDir(rootFile);
        } else {
            appRootDirPath = this.getServletConfig().getServletContext().getRealPath("/") + Constant.TEST_APP_DIR_NAME;
            rootFile = new File(appRootDirPath);
            mkdirOnlineDir(rootFile);
        }

    }

    private void mkdirOnlineDir(File rootFile) {
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type", "application/json;charset=UTF-8");

        List<String> dirNameList = new ArrayList<>();

        File parentDirectory = new File(appRootDirPath);
        if (parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }
        File[] tempList = parentDirectory.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {
                dirNameList.add(tempList[i].getName());
            }
        }
        String userJson = JSON.toJSONString(dirNameList);
        OutputStream out = resp.getOutputStream();
        out.write(userJson.getBytes("UTF-8"));
        out.flush();
    }
}