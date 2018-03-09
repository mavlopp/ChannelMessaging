package smith.alaric.channelmessaging.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import smith.alaric.channelmessaging.FriendsActivity;
import smith.alaric.channelmessaging.HttpPostHandler;
import smith.alaric.channelmessaging.LoginActivity;
import smith.alaric.channelmessaging.MainActivity;
import smith.alaric.channelmessaging.MyArrayAdapter;
import smith.alaric.channelmessaging.OnDownloadListener;
import smith.alaric.channelmessaging.PostRequest;
import smith.alaric.channelmessaging.R;
import smith.alaric.channelmessaging.model.ChannelList;

/**
 * Created by smithal on 26/02/2018.
 */
public class ChannelListFragment extends Fragment implements OnDownloadListener, View.OnClickListener  {
    private ListView lv;
    private Button friends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_channel,container);
        lv = (ListView)v.findViewById(R.id.listView);
        friends = (Button)v.findViewById(R.id.btFriends);
        Context c = getActivity().getApplicationContext();

        String token;
        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        token = settings.getString("accesstoken", null);

        //Toast t = Toast.makeText(c, token, Toast.LENGTH_LONG);
        friends.setOnClickListener(this);

        HashMap<String, String> myMap = new HashMap<String, String>();
        myMap.put("accesstoken", token);
        PostRequest p = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=getchannels", myMap);
        HttpPostHandler handler = new HttpPostHandler();
        handler.addOnDownloadListener(this);
        handler.execute(p);
        return v;
    }

    @Override
    public void onDownloadComplete(String downloadedContent) {
        Gson gson = new Gson();
        ChannelList obj = gson.fromJson(downloadedContent, ChannelList.class);

        lv.setAdapter(new MyArrayAdapter(getActivity().getApplicationContext(), obj.getList()));
        lv.setOnItemClickListener((MainActivity)getActivity());
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
        Intent i = new Intent(getContext(), FriendsActivity.class);
        startActivity(i);
    }
}
