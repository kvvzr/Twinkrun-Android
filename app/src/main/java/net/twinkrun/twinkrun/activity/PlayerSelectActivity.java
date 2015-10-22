package net.twinkrun.twinkrun.activity;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.twinkrun.twinkrun.R;
import net.twinkrun.twinkrun.adapter.PlayerSelectAdapter;
import net.twinkrun.twinkrun.entity.Player;
import net.twinkrun.twinkrun.helper.BleHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerSelectActivity extends BaseBleActivity {

    @Bind(R.id.player_list_view)
    ListView mPlayerListView;

    @OnClick(R.id.play_button)
    public void onClickPlayButton() {
        if (mPlayerSelectAdapter != null && mPlayerSelectAdapter.readyForPlaying()) {
            startActivity(new Intent(this, GameActivity.class));
            BleHelper.stopAdvertiseAndScan(mBleAdvertiser, mBleScanner);
        }
    }

    private PlayerSelectAdapter mPlayerSelectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);
        setTitle(R.string.player_select_title);

        ButterKnife.bind(this);

        mPlayerSelectAdapter = new PlayerSelectAdapter(this, new ArrayList<Pair<String, Player>>());
        mPlayerListView.setAdapter(mPlayerSelectAdapter);
        mPlayerListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mPlayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mPlayerSelectAdapter.getItem(position).second.togglePlayWith();
            }
        });

        // FIXME: 一時的にテスト用に入れる
        mPlayerSelectAdapter.add(new Pair<>("address", new Player("nico,830")));
        mPlayerListView.setItemChecked(0, true);

        setupBle("kwzr,25", getResources().getString(R.string.advertise_uuid), mScanCallback);
    }

    private final ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            String key = result.getDevice().getAddress();
            String name = result.getDevice().getName();
            if (mPlayerSelectAdapter.containsKey(key)) {
                int position = mPlayerSelectAdapter.getPlayerPosition(key);
                Player player = mPlayerSelectAdapter.getItem(position).second;
                if (!player.getName().equals(name)) {
                    player.setFromBleName(name);
                }
            } else if (name != null) {
                try {
                    mPlayerSelectAdapter.add(new Pair<>(key, new Player(name)));
                    mPlayerListView.setItemChecked(mPlayerSelectAdapter.getCount() - 1, true);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
