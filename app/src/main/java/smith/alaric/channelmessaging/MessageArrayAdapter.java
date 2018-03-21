package smith.alaric.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import smith.alaric.channelmessaging.model.Channel;
import smith.alaric.channelmessaging.model.Message;

/**
 * Created by smithal on 29/01/2018.
 */

public class MessageArrayAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private final ArrayList<Message> values;
    private ImageView img;
    public MessageArrayAdapter(Context context, ArrayList<Message> values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.message_layout, parent, false);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        TextView user = (TextView) rowView.findViewById(R.id.user);
        TextView message = (TextView) rowView.findViewById(R.id.message);
        img = (ImageView) rowView.findViewById(R.id.ivSent);
        date.setText(values.get(position).getDate());
        user.setText(values.get(position).getUsername()+"  ");
        message.setText(values.get(position).getMessage());
        loadImageFromUrl(values.get(position).getImageUrl());
        return rowView;
    }

    public void loadImageFromUrl(String url) {
        Glide.with(getContext()).load(url).override(1200, 400).into(img);
    }
}
