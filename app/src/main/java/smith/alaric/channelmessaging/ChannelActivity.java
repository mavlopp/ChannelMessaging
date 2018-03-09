package smith.alaric.channelmessaging;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import smith.alaric.channelmessaging.model.MessageList;

/**
 * Created by smithal on 29/01/2018.
 */
public class ChannelActivity extends Activity implements OnDownloadListener, View.OnClickListener {
    private ListView lv;
    private EditText tv;
    private Button send;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        Context c = getApplicationContext();

        lv = (ListView) findViewById(R.id.listMsg);
        tv = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(this);

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                Post();
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }

    @Override
    public void onDownloadComplete(String downloadedContent) {
        Gson gson = new Gson();
        MessageList obj = gson.fromJson(downloadedContent, MessageList.class);

        lv.setAdapter(new MessageArrayAdapter(getApplicationContext(), obj.getList()));
    }

    @Override
    public void onDownloadError(String error) {
        Context c = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, error, duration);
        t.show();
    }


    @Override
    public void onClick(View v) {
        String token;
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        token = settings.getString("accesstoken", null);

        Long chan_id;
        chan_id = getIntent().getLongExtra("CHAN_ID",999);

        HashMap<String, String> myMap = new HashMap<String, String>();
        myMap.put("accesstoken", token);
        myMap.put("channelid", chan_id.toString());
        myMap.put("message", tv.getText().toString());

        PostRequest p = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=sendmessage", myMap);
        HttpPostHandler handler = new HttpPostHandler();
        handler.addOnDownloadListener(this);
        handler.execute(p);
    }

    public void Post(){
        String token;
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        token = settings.getString("accesstoken", null);

        Long chan_id;
        chan_id = getIntent().getLongExtra("CHAN_ID",999);
        //chan_id = bundle.getString("CHAN_ID");

        HashMap<String, String> myMap = new HashMap<String, String>();
        myMap.put("accesstoken", token);
        myMap.put("channelid", ""+chan_id);
        PostRequest p = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=getmessages", myMap);
        HttpPostHandler handler = new HttpPostHandler();
        handler.addOnDownloadListener(this);
        handler.execute(p);
    }
}
