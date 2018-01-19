package smith.alaric.channelmessaging;

import java.util.HashMap;

/**
 * Created by smithal on 19/01/2018.
 */
public class PostRequest {
    private String url;
    private HashMap<String, String> postParams;

    public PostRequest(String url, HashMap<String, String> postParams) {
        this.url = url;
        this.postParams = postParams;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getPostParams() {
        return postParams;
    }
}
