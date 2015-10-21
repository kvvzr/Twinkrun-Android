package net.twinkrun.twinkrun.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.twinkrun.twinkrun.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayerSelectActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 25252;
    private final static int REQUEST_COARSE_LOCATION = 83025;

    @Bind(R.id.player_list_view)
    ListView mPlayerListView;

    private BluetoothManager mBleManager;
    private BluetoothAdapter mBleAdapter;
    private BluetoothLeScanner mBleScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);
        setTitle(R.string.player_select_title);

        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, new ArrayList<String>());
        mPlayerListView.setAdapter(adapter);
        mPlayerListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
        } else {
            setupBle();
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
            startScan();
        }
    }

    private void setupBle() {
        mBleManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBleAdapter = mBleManager.getAdapter();

        if (mBleAdapter == null || !mBleAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
        } else {
            startScan();
        }
    }

    private void startScan() {
        mBleScanner = mBleAdapter.getBluetoothLeScanner();
        mBleScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                Log.d("PlayerSelectActivity", result.getDevice().getName());
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
            }
        });
    }
}
