package smith.alaric.channelmessaging.model;

/**
 * Created by smithal on 22/01/2018.
 */
public class Channel {
    private int id;
    private String name;
    private int connectedusers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConnectedusers() {
        return connectedusers;
    }

    public void setConnectedusers(int connectedusers) {
        this.connectedusers = connectedusers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Channel(){

    }
}
