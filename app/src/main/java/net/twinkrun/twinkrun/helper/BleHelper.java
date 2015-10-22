package net.twinkrun.twinkrun.helper;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.os.ParcelUuid;

import java.util.UUID;

public class BleHelper {

    public static void startAdvertiseAndScan(String deviceName, String uuidString, BluetoothAdapter adapter, BluetoothLeAdvertiser advertiser, BluetoothLeScanner scanner, ScanCallback callback) {
        // FIXME: 適切なデータにする
        adapter.setName(deviceName);

        advertiser = adapter.getBluetoothLeAdvertiser();
        advertiser.startAdvertising(makeAdvertiseSetting(), makeAdvertiseData(uuidString), new EmptyAdvertiseCallback());

        scanner = adapter.getBluetoothLeScanner();
        scanner.startScan(callback);
    }

    public static void stopAdvertiseAndScan(BluetoothLeAdvertiser advertiser, BluetoothLeScanner scanner) {
        if (advertiser != null) {
            advertiser.stopAdvertising(new EmptyAdvertiseCallback());
            advertiser = null;
        }
        if (scanner != null) {
            scanner.stopScan(null);
            scanner = null;
        }
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
        return builder.build();
    }

    private static AdvertiseData makeAdvertiseData(String uuidString) {
        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addServiceUuid(new ParcelUuid(UUID.fromString(uuidString)));
        builder.setIncludeDeviceName(true);
        return builder.build();
    }
}
