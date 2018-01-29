package smith.alaric.channelmessaging.model;

/**
 * Created by smithal on 29/01/2018.
 */
public class Message {

    private int userID;
    private String message;
    private String date;
    private String imageUrl;

    public int getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDate() {
        return date;
    }

    public Message(){

    }
}
