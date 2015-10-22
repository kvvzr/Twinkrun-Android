package net.twinkrun.twinkrun.activity;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import net.twinkrun.twinkrun.R;
import net.twinkrun.twinkrun.entity.Player;
import net.twinkrun.twinkrun.model.Game;
import net.twinkrun.twinkrun.model.IGame;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends BaseBleActivity implements IGame {

    private Game mGame;

    @Bind(R.id.background)
    View mBackGround;

    @Bind(R.id.play_button)
    FloatingActionButton mPlayButton;

    @Bind(R.id.count_down_text_view)
    TextView mCountDownTextView;

    @OnClick(R.id.play_button)
    public void onClickPlayButton() {
        mGame.togglePlayState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // FIXME: PlayerSelectActivityからデータを持ってくる
        mGame = new Game(this, new Player("kwzr,25"), new ArrayList<Player>());
        setupBle("kwzr,25", getResources().getString(R.string.advertise_uuid), mScanCallback);
    }

    private final ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            mGame.addScanResult(result);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGame.stop();
    }

    @Override
    public void onStartCountDown() {
        Log.d("GameActivity", "onStartCountDown");
        mPlayButton.setAlpha(0.5f);
        mCountDownTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUpdateCountDown(int count) {
        Log.d("GameActivity", "onUpdateCountDown: " + count);
        mCountDownTextView.setText("" + count);
    }

    @Override
    public void onStartGame() {
        Log.d("GameActivity", "onStartGame");
        mCountDownTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onUpdateScore(int score) {
        Log.d("GameActivity", "onUpdateScore");
    }

    @Override
    public void onUpdateRole(int color, int score) {
        Log.d("GameActivity", "onUpdateRole");
        double rand = Math.random();
        if (rand < 0.33) {
            mBackGround.setBackgroundColor(ContextCompat.getColor(this, R.color.twinkrunBlack));
        } else if (rand < 0.66) {
            mBackGround.setBackgroundColor(ContextCompat.getColor(this, R.color.twinkrunGreen));
        } else {
            mBackGround.setBackgroundColor(ContextCompat.getColor(this, R.color.twinkrunRed));
        }
    }

    @Override
    public void onFlash(int color) {
        Log.d("GameActivity", "onFlash");
    }

    @Override
    public void onStopGame() {
        Log.d("GameActivity", "onStopGame");
        mBackGround.setBackgroundColor(ContextCompat.getColor(this, R.color.twinkrunLightBlack));
        mPlayButton.setAlpha(1f);
        mCountDownTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onEndGame() {
        Log.d("GameActivity", "onEndGame");
        startActivity(new Intent(this, ResultActivity.class));
    }
}
