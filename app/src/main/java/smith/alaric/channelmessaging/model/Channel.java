package smith.alaric.channelmessaging.model;

/**
 * Created by smithal on 22/01/2018.
 */
public class Channel {
    private int channelID;
    private String name;
    private int connectedusers;

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
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
