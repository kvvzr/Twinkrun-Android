package net.twinkrun.twinkrun.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.twinkrun.twinkrun.R;

import butterknife.ButterKnife;

public class PlayerSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);
        setTitle(R.string.player_select_title);

        ButterKnife.bind(this);
    }
}
