package smith.alaric.channelmessaging.db;

import java.util.UUID;

/**
 * Created by smithal on 05/02/2018.
 */
public class Friend {

    private UUID id;
    private String username;
    private String imageUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Friend(String imageUrl, String username) {
        this.imageUrl = imageUrl;
        this.username = username;
    }

    public Friend() {

    }
}
