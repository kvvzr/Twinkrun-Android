package net.twinkrun.twinkrun.helper;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.os.ParcelUuid;
import android.util.Log;

import java.util.UUID;

public class BleHelper {

    public static void startAdvertiseAndScan(String deviceName, String uuidString, BluetoothAdapter adapter, ScanCallback callback) {
        // FIXME: 適切なデータにする
        adapter.setName(deviceName);

        BluetoothLeAdvertiser advertiser = adapter.getBluetoothLeAdvertiser();
        advertiser.startAdvertising(makeAdvertiseSetting(), makeAdvertiseData(uuidString), new EmptyAdvertiseCallback());

        BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
        scanner.startScan(callback);
    }

    public static void stopAdvertiseAndScan(BluetoothAdapter adapter) {
        BluetoothLeAdvertiser advertiser = adapter.getBluetoothLeAdvertiser();
        BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();

        if (advertiser != null) {
            Log.d("BleHelper", "Stop Adv");
            advertiser.stopAdvertising(new EmptyAdvertiseCallback());
        }
        if (scanner != null) {
            Log.d("BleHelper", "Stop Scan");
            scanner.stopScan(null);
        }
        adapter.disable();
    }

    public static final class EmptyAdvertiseCallback extends AdvertiseCallback {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
        }

        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
        }
    }

    private static AdvertiseSettings makeAdvertiseSetting() {
        AdvertiseSettings.Builder builder = new AdvertiseSettings.Builder();
        builder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
        builder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM);
        builder.setConnectable(false);
        builder.setTimeout(0);
        return builder.build();
    }

    private static AdvertiseData makeAdvertiseData(String uuidString) {
        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addServiceUuid(new ParcelUuid(UUID.fromString(uuidString)));
        builder.addManufacturerData(0, "ぽよ".getBytes());
        //builder.addServiceData(new ParcelUuid(UUID.fromString(uuidString)), "ぽよ".getBytes());
        //builder.addServiceUuid(new ParcelUuid(UUID.fromString("CDB546E0-CC68-4494-8C39-81B542A35A2A")));
        builder.setIncludeDeviceName(true);
        return builder.build();
    }
}
