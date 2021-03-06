package net.twinkrun.twinkrun.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

        Pair<String, Player> item = getItem(position);
        if (item != null) {
            CheckedTextView textView = (CheckedTextView) convertView.findViewById(android.R.id.text1);
            textView.setText(item.second.getName());
        }
        return convertView;
    }

    public boolean containsKey(@NonNull String key) {
        for (int i = 0; i < getCount(); i++) {
            Pair<String, Player> item = getItem(i);
            if (item.first.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public int getPlayerPosition(@NonNull String key) {
        for (int i = 0; i < getCount(); i++) {
            Pair<String, Player> item = getItem(i);
            if (item.first.equals(key)) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean readyForPlaying() {
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).second.playWith()) {
                return true;
            }
        }
        return false;
    }
}
