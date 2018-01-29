package smith.alaric.channelmessaging.model;

import java.util.ArrayList;

/**
 * Created by smithal on 29/01/2018.
 */
public class MessageList {

    private ArrayList<Message> messages = new ArrayList<Message>();

    public ArrayList<Message> getList() {
        return messages;
    }

    public void setList(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public MessageList(){

    }

}
