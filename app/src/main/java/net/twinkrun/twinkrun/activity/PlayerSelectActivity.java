package net.twinkrun.twinkrun.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.ListView;

import net.twinkrun.twinkrun.R;
import net.twinkrun.twinkrun.adapter.PlayerSelectAdapter;
import net.twinkrun.twinkrun.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayerSelectActivity extends AppCompatActivity {

    private final static String ADVERTISE_UUID = "4C866825-6CF5-48DD-8C64-D9EC109CB3E4";
    private final static int REQUEST_ENABLE_BT = 25252;
    private final static int REQUEST_COARSE_LOCATION = 83025;

    @Bind(R.id.player_list_view)
    ListView mPlayerListView;

    private PlayerSelectAdapter mPlayerSelectAdapter;
    private BluetoothManager mBleManager;
    private BluetoothAdapter mBleAdapter;
    private BluetoothLeAdvertiser mBleAdvertiser;
    private BluetoothLeScanner mBleScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);
        setTitle(R.string.player_select_title);

        ButterKnife.bind(this);

        mPlayerSelectAdapter = new PlayerSelectAdapter(this, new ArrayList<Pair<String, Player>>());
        mPlayerListView.setAdapter(mPlayerSelectAdapter);
        mPlayerListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
        } else {
            setupBle();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBleAdvertiser != null) {
            mBleAdvertiser.stopAdvertising(new EmptyAdvertiseCallback());
            mBleAdvertiser = null;
        }
        if (mBleScanner != null) {
            mBleScanner.stopScan(null);
            mBleScanner = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (REQUEST_COARSE_LOCATION == requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupBle();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && mBleAdapter != null && mBleAdapter.isEnabled()) {
            startAdvertiseAndScan();
        }
    }

    private void setupBle() {
        mBleManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBleAdapter = mBleManager.getAdapter();

        if (mBleAdapter == null || !mBleAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
        } else {
            startAdvertiseAndScan();
        }
    }

    private void startAdvertiseAndScan() {
        // FIXME: 適切なデータにする
        mBleAdapter.setName("kwzr,25");

        mBleAdvertiser = mBleAdapter.getBluetoothLeAdvertiser();
        mBleAdvertiser.startAdvertising(makeAdvertiseSetting(), makeAdvertiseData(), new EmptyAdvertiseCallback());

        mBleScanner = mBleAdapter.getBluetoothLeScanner();
        mBleScanner.startScan(new ScanCallback() {
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
                    mPlayerSelectAdapter.add(new Pair<>(key, new Player(name)));
                    mPlayerListView.setItemChecked(mPlayerSelectAdapter.getCount() - 1, true);
                }
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        });
    }

    private AdvertiseSettings makeAdvertiseSetting() {
        AdvertiseSettings.Builder builder = new AdvertiseSettings.Builder();
        builder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
        builder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM);
        builder.setConnectable(false);
        return builder.build();
    }

    private AdvertiseData makeAdvertiseData() {
        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addServiceUuid(new ParcelUuid(UUID.fromString(ADVERTISE_UUID)));
        builder.setIncludeDeviceName(true);
        return builder.build();
    }

    private class EmptyAdvertiseCallback extends AdvertiseCallback {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
        }

        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
        }
    }
}
