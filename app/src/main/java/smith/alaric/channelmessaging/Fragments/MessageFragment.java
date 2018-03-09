package smith.alaric.channelmessaging.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import smith.alaric.channelmessaging.HttpPostHandler;
import smith.alaric.channelmessaging.LoginActivity;
import smith.alaric.channelmessaging.MessageArrayAdapter;
import smith.alaric.channelmessaging.OnDownloadListener;
import smith.alaric.channelmessaging.PostRequest;
import smith.alaric.channelmessaging.R;
import smith.alaric.channelmessaging.db.UserDatasource;
import smith.alaric.channelmessaging.model.Message;
import smith.alaric.channelmessaging.model.MessageList;

/**
 * Created by smithal on 26/02/2018.
 */
public class MessageFragment extends Fragment implements OnDownloadListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView lv;
    private EditText tv;
    private Button send;
    private long chanId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message, container);
        Context c = getActivity().getApplicationContext();

        lv = (ListView) v.findViewById(R.id.listView2);
        tv = (EditText) v.findViewById(R.id.editText);
        send = (Button) v.findViewById(R.id.send);

        send.setOnClickListener(this);

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                Post(chanId);
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
        lv.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onDownloadComplete(String downloadedContent) {
        if(getActivity() != null) {
            Gson gson = new Gson();
            MessageList obj = gson.fromJson(downloadedContent, MessageList.class);

            lv.setAdapter(new MessageArrayAdapter(getActivity().getApplicationContext(), obj.getList()));
        }

    }

    @Override
    public void onDownloadError(String error) {
        Context c = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, error, duration);
        t.show();
    }


    @Override
    public void onClick(View v) {
        String token;
        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        token = settings.getString("accesstoken", null);

        HashMap<String, String> myMap = new HashMap<String, String>();
        myMap.put("accesstoken", token);
        myMap.put("channelid", String.valueOf(chanId));
        myMap.put("message", tv.getText().toString());

        PostRequest p = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=sendmessage", myMap);
        HttpPostHandler handler = new HttpPostHandler();
        handler.addOnDownloadListener(this);
        handler.execute(p);
        tv.setText("");
    }

    public void Post() {
        Long chan_id;
        chan_id = getActivity().getIntent().getLongExtra("CHAN_ID",999);
        //chan_id = bundle.getString("CHAN_ID");
        Post(chan_id);
    }

    public void Post(double chan_id){
        String token = "";
        if(getActivity() != null) {
            SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
            token = settings.getString("accesstoken", null);
        }



        HashMap<String, String> myMap = new HashMap<String, String>();
        myMap.put("accesstoken", token);
        myMap.put("channelid", ""+chan_id);
        PostRequest p = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=getmessages", myMap);
        HttpPostHandler handler = new HttpPostHandler();
        handler.addOnDownloadListener(this);
        handler.execute(p);
    }

    public void changeChannel(final long chan_id){
        this.chanId = chan_id;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        final Message item = (Message) parent.getItemAtPosition(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Attention")
                .setMessage("Voulez-vous ajouter cette personne à votre liste d'amis ?")
                .setCancelable(false)
                .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserDatasource ud = new UserDatasource(getContext());
                        ud.open();
                        ud.createFriend(item.getUsername(), item.getImageUrl());
                        ud.close();
                    }
                }).setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
