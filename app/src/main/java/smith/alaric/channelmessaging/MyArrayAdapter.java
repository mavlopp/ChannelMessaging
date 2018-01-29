package smith.alaric.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import smith.alaric.channelmessaging.model.Channel;

/**
 * Created by smithal on 29/01/2018.
 */
public class MyArrayAdapter extends ArrayAdapter<Channel> {
    private final Context context;
    private final ArrayList<Channel> values;
    public MyArrayAdapter(Context context, ArrayList<Channel> values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView infos = (TextView) rowView.findViewById(R.id.infos);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        title.setText(values.get(position).getName());
        infos.setText(""+values.get(position).getConnectedusers()+" utilisateur(s) connecté(s)");
        //String s = values.indexOf();
        /*
        if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.no);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }*/
        return rowView;
    }
}
