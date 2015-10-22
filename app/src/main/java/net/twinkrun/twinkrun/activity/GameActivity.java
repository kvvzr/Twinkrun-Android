package net.twinkrun.twinkrun.activity;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.graphics.Color;
import android.os.Bundle;

import net.twinkrun.twinkrun.R;
import net.twinkrun.twinkrun.model.Game;
import net.twinkrun.twinkrun.model.IGame;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends BaseBleActivity implements IGame {

    private Game mGame;

    @OnClick(R.id.play_button)
    public void onClickPlayButton() {
        mGame.togglePlayState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mGame = new Game(this);
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
    public void onUpdateCountDown(int count) {

    }

    @Override
    public void onStartGame() {

    }

    @Override
    public void onUpdateScore(int score) {

    }

    @Override
    public void onFlash(Color color) {

    }

    @Override
    public void onUpdateRole(Color color, int score) {

    }

    @Override
    public void onEndGame() {

    }
}
