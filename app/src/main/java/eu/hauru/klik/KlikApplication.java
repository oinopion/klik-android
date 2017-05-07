package eu.hauru.klik;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import io.realm.Realm;

public class KlikApplication extends Application {
    public static final String TAG = "KlikApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        if (BuildConfig.DEBUG) {
            enableStrictMode();
        }
    }

    private void enableStrictMode() {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build();

        StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build();

        StrictMode.setThreadPolicy(threadPolicy);
        StrictMode.setVmPolicy(vmPolicy);

        Log.i(TAG, "Strict mode enabled");
    }
}
