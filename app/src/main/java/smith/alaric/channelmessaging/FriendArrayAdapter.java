package smith.alaric.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import smith.alaric.channelmessaging.db.Friend;
import smith.alaric.channelmessaging.model.Message;
/**
 * Created by smithal on 29/01/2018.
 */

public class FriendArrayAdapter extends ArrayAdapter<Friend> {

    public TextView user;
    private final Context context;
    private final List<Friend> values;
    public FriendArrayAdapter(Context context, List<Friend> values) {
        super(context, R.layout.friend_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.message_layout, parent, false);
        TextView img = (TextView) rowView.findViewById(R.id.tvImg);
        user = (TextView) rowView.findViewById(R.id.tvUsrname);
        user.setText(values.get(position).getUsername());
        img.setText(values.get(position).getImageUrl());
        return rowView;
    }
}
