package hellogbye.com.hellogbyeandroid.models;

/**
 * Created by arisprung on 2/14/17.
 */

public class ShareClass {
    private String name;
    private String url;

    public ShareClass(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}