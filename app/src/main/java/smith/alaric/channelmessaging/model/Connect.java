package smith.alaric.channelmessaging.model;

/**
 * Created by smithal on 22/01/2018.
 */
public class Connect {

    private String response;
    private int code;
    private String accesstoken;

    public void setResponse(String response) {
        this.response = response;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getResponse() {
        return response;
    }

    public int getCode() {
        return code;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public Connect(){

    }
}
