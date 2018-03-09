package smith.alaric.channelmessaging;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import smith.alaric.channelmessaging.db.Friend;
import smith.alaric.channelmessaging.db.UserDatasource;

/**
 * Created by smithal on 07/03/2018.
 */
public class FriendsActivity extends Activity {

    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        List<Friend> mesAmis = new ArrayList<Friend>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        lv = (ListView) findViewById(R.id.lv);
        Context c = getApplicationContext();

        UserDatasource ud = new UserDatasource(getApplicationContext());
        ud.open();
        mesAmis = ud.getAllFriends();
        ud.close();

        lv.setAdapter(new FriendArrayAdapter(getApplicationContext(),mesAmis));
    }
}
