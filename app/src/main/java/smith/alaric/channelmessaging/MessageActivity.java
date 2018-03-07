package smith.alaric.channelmessaging;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import smith.alaric.channelmessaging.Fragments.MessageFragment;

/**
 * Created by smithal on 29/01/2018.
 */
public class MessageActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        Long channelId = getIntent().getLongExtra("CHAN_ID", 1337);
        MessageFragment fragB = (MessageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentB_ID);
        fragB.changeChannel(channelId);
    }
}
