package testtask.slatestudio.locationchecker.tracking.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * @author Roman
 * @since 1/9/2018.
 */
public class WifiBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = WifiBroadcastReceiver.class.getSimpleName();
    private static final String EMPTY = "";
    private WifiReceiverCallback callback;

    public WifiBroadcastReceiver(WifiReceiverCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
            SupplicantState state = intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
            if (SupplicantState.isValidState(state)) {
                callback.onCurrentWifiChanged(getWifiZoneName(context));
            }
        }
    }

    private String getWifiZoneName(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.getConnectionInfo() != null) {
            String currentWifiName = wifiManager.getConnectionInfo().getSSID().replace("\"", "");
            Log.d(TAG, "currentWifiName : " + currentWifiName);
            return currentWifiName;
        }
        return EMPTY;
    }
}
