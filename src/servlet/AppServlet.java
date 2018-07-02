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
 * 测试APPServle
 */
@WebServlet("/AppServlet")
public class AppServlet extends HttpServlet {


    /**
     * 获取app信息
     *
     * @param req  请求
     * @param resp 应答
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type", "application/json;charset=UTF-8");

        List<String> appPathList = new ArrayList<>();

        String testAppPath = this.getServletConfig().getServletContext().getRealPath("/") + Constant.TEST_APP_DIR_NAME;
        File parentDirectory = new File(testAppPath);
        if (parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }
        File[] tempList = parentDirectory.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                appPathList.add(Constant.TEST_APP_DIR_NAME + "/" + tempList[i].getName());
            }
        }
        String userJson = JSON.toJSONString(appPathList);
        OutputStream out = resp.getOutputStream();
        out.write(userJson.getBytes("UTF-8"));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


}