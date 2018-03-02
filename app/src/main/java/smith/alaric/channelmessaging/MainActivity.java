package smith.alaric.channelmessaging;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import smith.alaric.channelmessaging.Fragments.ChannelListFragment;
import smith.alaric.channelmessaging.Fragments.MessageFragment;
import smith.alaric.channelmessaging.model.Message;

/**
 * Created by smithal on 26/02/2018.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChannelListFragment fragA = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentA_ID);
        MessageFragment fragB = (MessageFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentB_ID);
        if(fragB == null|| !fragB.isInLayout()){
            Intent i = new Intent(getApplicationContext(),MessageActivity.class);
            i.putExtra("CHAN_ID", id);
            startActivity(i);
        } else {
            //fragB.fillTextView(fragA.listItems[position]);
            fragB.changeChannel(id);
            Context c = getApplicationContext();
        }
    }
}
