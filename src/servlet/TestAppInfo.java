package servlet;

public class TestAppInfo {

    private String name;
    private String downloadPath;

    public TestAppInfo() {
    }

    public TestAppInfo(String name, String downloadPath) {
        this.name = name;
        this.downloadPath = downloadPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }
}