package net.twinkrun.twinkrun.activity;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Bundle;

import net.twinkrun.twinkrun.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends BaseBleActivity {

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

        setupBle("kwzr,25", getResources().getString(R.string.advertise_uuid), mScanCallback);
    }

    private final ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
        }
    };
}
