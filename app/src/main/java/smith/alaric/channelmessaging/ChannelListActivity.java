package smith.alaric.channelmessaging;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import smith.alaric.channelmessaging.model.Channel;
import smith.alaric.channelmessaging.model.ChannelList;
import smith.alaric.channelmessaging.model.Connect;

/**
 * Created by smithal on 22/01/2018.
 */
public class ChannelListActivity extends Activity implements OnDownloadListener, AdapterView.OnItemClickListener {

    private ListView lv;
    private TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_channel);
        Context c = getApplicationContext();

        lv = (ListView) findViewById(R.id.listView);
        tv = (TextView) findViewById(R.id.textView);


        String token;
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        token = settings.getString("accesstoken", null);

        //Toast t = Toast.makeText(c, token, Toast.LENGTH_LONG);

        HashMap<String, String> myMap = new HashMap<String, String>();
        myMap.put("accesstoken", token);
        PostRequest p = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=getchannels", myMap);
        HttpPostHandler handler = new HttpPostHandler();
        handler.addOnDownloadListener(this);
        handler.execute(p);


    }

    @Override
    public void onDownloadComplete(String downloadedContent) {
        //tv.setText(downloadedContent.toString());

        Gson gson = new Gson();
        ChannelList obj = gson.fromJson(downloadedContent, ChannelList.class);

        lv.setAdapter(new MyArrayAdapter(getApplicationContext(), obj.getList()));
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onDownloadError(String error) {
        Context c = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, error, duration);
        t.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
