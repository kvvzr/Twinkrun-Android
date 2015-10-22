package net.twinkrun.twinkrun.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.twinkrun.twinkrun.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    @OnClick(R.id.play_button)
    public void onClickPlayButton() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}
