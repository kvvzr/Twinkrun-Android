package net.twinkrun.twinkrun.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.twinkrun.twinkrun.R;

public class PlayerSelectFragment extends Fragment {

    public PlayerSelectFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player_select, container, false);
    }

}
