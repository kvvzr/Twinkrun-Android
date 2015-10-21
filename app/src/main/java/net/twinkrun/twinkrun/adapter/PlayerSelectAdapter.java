package net.twinkrun.twinkrun.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import net.twinkrun.twinkrun.entity.Player;

import java.util.List;

public class PlayerSelectAdapter extends ArrayAdapter<Pair<String, Player>> {

    public PlayerSelectAdapter(Context context, List<Pair<String, Player>> objects) {
        super(context, android.R.layout.simple_list_item_multiple_choice, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        }

        Pair<String, Player> item = this.getItem(position);
        if (item != null) {
            CheckedTextView textView = (CheckedTextView) convertView.findViewById(android.R.id.text1);
            textView.setText(item.second.getName());
        }
        return convertView;
    }

    public boolean containsKey(String key) {
        for (int i = 0; i < getCount(); i++) {
            Pair<String, Player> item = this.getItem(i);
            if (item.first.equals(key)) {
                return true;
            }
        }
        return false;
    }
}
