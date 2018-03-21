package smith.alaric.channelmessaging;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import smith.alaric.channelmessaging.db.Friend;
import smith.alaric.channelmessaging.model.Message;
/**
 * Created by smithal on 29/01/2018.
 */

public class FriendArrayAdapter extends ArrayAdapter<Friend> {
    private TextView img;
    //private ImageView img;
    private TextView user;
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
        View rowView = inflater.inflate(R.layout.friend_layout, parent, false);
        img = (TextView) rowView.findViewById(R.id.tvImg);
        //img = (ImageView) rowView.findViewById(R.id.ivImg);
        user = (TextView) rowView.findViewById(R.id.tvUsrname);
        user.setText(values.get(position).getUsername());
        img.setText(values.get(position).getImageUrl());
        //Picasso.with(context).load(values.get(position).getImageUrl()).into(img);
        return rowView;
    }
}
