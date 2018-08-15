package restaurant.study.com.test_weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceUtil {
    private static Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private SharedPreferenceUtil(Context context) {
        preferences = context.getSharedPreferences("Application", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    private static SharedPreferenceUtil instance;
    public static SharedPreferenceUtil getInstance(Context context) {
        if (instance == null)
            instance = new SharedPreferenceUtil(context);

        return instance;
    }



    public static final String keyLongitude = "Longitude";
    public static final String keyLatitude = "Latitude";

    public void setLongitude(float longitude) {
        editor.putFloat(keyLongitude, longitude);
        editor.commit();
    }
    public Float getLongitude() {
        return preferences.getFloat(keyLongitude, 0);
    }
    public void clearLongitude() {
        editor.remove(keyLongitude);
        editor.apply();
    }

    public void setLatitude(Float latitude) {
        editor.putFloat(keyLatitude, latitude);
        editor.commit();
    }

    public Float getLatitude() {
        return preferences.getFloat(keyLatitude, 0);
    }

    public void clearLatitude() {
        editor.remove(keyLatitude);
        editor.apply();
    }
}
