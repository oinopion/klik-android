package eu.hauru.klik;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

class Repo {
    private final static String REPO_FILE = "repo";
    private final static String KEY_LATEST_ID = "latestId";
    private final static String KEY_SUFFIX_NAME = ":name";
    private final static String KEY_SUFFIX_VALUE = ":value";

    private final SharedPreferences preferences;
    private UUID currentId;

    public Repo(Context context) {
        this.preferences = context.getSharedPreferences(REPO_FILE, Context.MODE_PRIVATE);
    }

    public String getName() {
        return getName(null);
    }

    public String getName(String defaultName) {
        return preferences.getString(getNameKey(), defaultName);
    }

    public void setName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getNameKey(), name);
        editor.apply();
    }

    public int getValue() {
        return preferences.getInt(getValueKey(), 0);
    }

    public void incrementValue() {
        setValue(getValue() + 1);
    }

    public void resetValue() {
        setValue(0);
    }

    private UUID getCurrentId() {
        if (currentId == null) {
            currentId = getOrCreateLatestId();
        }

        return currentId;
    }

    private UUID getOrCreateLatestId() {
        String idString = preferences.getString(KEY_LATEST_ID, null);
        if (idString != null) {
            return UUID.fromString(idString);
        } else {
            UUID newId = UUID.randomUUID();
            setLatestId(newId);
            return newId;
        }
    }

    private void setLatestId(UUID id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_LATEST_ID, id.toString());
        editor.apply();
    }

    private void setValue(int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getValueKey(), value);
        editor.apply();
    }

    private String getNameKey() {
        return getCurrentId().toString() + KEY_SUFFIX_NAME;
    }

    private String getValueKey() {
        return getCurrentId().toString() + KEY_SUFFIX_VALUE;
    }
}
