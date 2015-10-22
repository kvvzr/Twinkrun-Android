package net.twinkrun.twinkrun.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import net.twinkrun.twinkrun.helper.BleHelper;

public class BaseBleActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 25252;
    private final static int REQUEST_COARSE_LOCATION = 83025;

    protected BluetoothAdapter mBleAdapter;
    protected BluetoothLeAdvertiser mBleAdvertiser = null;
    protected BluetoothLeScanner mBleScanner = null;

    protected String mDeviceName;
    protected String mUuidString;
    protected ScanCallback mScanCallback;

    protected void setupBle(String deviceName, String uuidString, ScanCallback callback) {
        mDeviceName = deviceName;
        mUuidString = uuidString;
        mScanCallback = callback;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
        } else {
            setupAdvertiseAndScan();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleHelper.stopAdvertiseAndScan(mBleAdvertiser, mBleScanner);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (REQUEST_COARSE_LOCATION == requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupAdvertiseAndScan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && mBleAdapter != null && mBleAdapter.isEnabled()) {
            startAdvertiseAndScan();
        }
    }

    private void setupAdvertiseAndScan() {
        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBleAdapter = manager.getAdapter();

        if (mBleAdapter == null || !mBleAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
        } else {
            startAdvertiseAndScan();
        }
    }

    private void startAdvertiseAndScan() {
        // FIXME: 適切なデータにする
        BleHelper.startAdvertiseAndScan(mDeviceName, mUuidString, mBleAdapter, mBleAdvertiser, mBleScanner, mScanCallback);
    }
}
