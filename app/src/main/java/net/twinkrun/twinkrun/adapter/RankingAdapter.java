package net.twinkrun.twinkrun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import net.twinkrun.twinkrun.entity.Player;

import java.util.List;

public class RankingAdapter extends ArrayAdapter<Player> {

    public RankingAdapter(Context context, List<Player> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        }

        Player item = getItem(position);
        if (item != null) {
            CheckedTextView textView = (CheckedTextView) convertView.findViewById(android.R.id.text1);
            textView.setText(item.getName());
        }
        return convertView;
    }
}
