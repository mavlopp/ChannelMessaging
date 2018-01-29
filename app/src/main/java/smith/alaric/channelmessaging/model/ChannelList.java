package smith.alaric.channelmessaging.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smithal on 22/01/2018.
 */
public class ChannelList {

    private ArrayList<Channel> channels = new ArrayList<Channel>();

    public ArrayList<Channel> getList() {
        return channels;
    }

    public void setList(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public ChannelList(){

    }
}
