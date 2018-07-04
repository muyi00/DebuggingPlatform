package servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 测试APPServle
 */
@WebServlet("/AppServlet")
public class AppServlet extends HttpServlet {

    String appRootDirPath = "";

    @Override
    public void init() throws ServletException {
        appRootDirPath = PropertyUtils.getPropertyByName("application", "appRootDirPath");
        if (appRootDirPath == null || appRootDirPath.length() == 0) {
            appRootDirPath = this.getServletConfig().getServletContext().getRealPath("/") + Constant.TEST_APP_DIR_NAME;
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type", "application/json;charset=UTF-8");


        String result = getJsonParameter(req);
        if (result == null || result.length() == 0) {
            return;
        }

        JSONObject jsonObject = JSON.parseObject(result);
        String dirName = jsonObject.getString("dirName");
        File appPath = new File(appRootDirPath, dirName);

        List<String> appPathList = new ArrayList<>();
        File[] tempList = appPath.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                appPathList.add(Constant.TEST_APP_DIR_NAME + "/" + dirName + "/" + tempList[i].getName());
            }
        }
        String userJson = JSON.toJSONString(appPathList);
        OutputStream out = resp.getOutputStream();
        out.write(userJson.getBytes("UTF-8"));
        out.flush();
    }


    private String getJsonParameter(HttpServletRequest req) throws IOException {
        InputStream inputStream = req.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String result = "";
        byte[] data = new byte[1024];
        int len = 0;
        result = "";
        if (inputStream != null) {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
            result = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
        }
        return result;
    }


}