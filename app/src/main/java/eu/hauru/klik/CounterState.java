package eu.hauru.klik;


import android.content.Context;
import android.content.SharedPreferences;


class CounterState {
    final static String STATE_PREFERENCES = "state";
    final static String COUNTER_VALUE_KEY = "counterValue";
    final static String COUNTER_NAME_KEY = "counterName";


    private int counterValue;
    private String counterName;

    private final SharedPreferences preferences;
    private final int initialCounterValue = 0;
    private final String defaultCounterName;

    public CounterState(Context context) {
        super();
        this.preferences = context.getSharedPreferences(STATE_PREFERENCES, Context.MODE_PRIVATE);
        this.defaultCounterName = context.getString(R.string.default_counter_name);

        this.counterValue = fetchCounterValue();
        this.counterName = fetchCounterName();
    }

    public int getValue() {
        return counterValue;
    }

    public String getName() {
        return counterName;
    }

    public void incrementValue() {
        counterValue += 1;
        saveCounterValue();
    }

    public void resetValue() {
        counterValue = 0;
        saveCounterValue();
    }

    public void changeName(String name) {
        this.counterName = name;
        saveCounterName();
    }

    private int fetchCounterValue() {
        return preferences.getInt(COUNTER_VALUE_KEY, initialCounterValue);
    }

    private String fetchCounterName() {
        return preferences.getString(COUNTER_NAME_KEY, defaultCounterName);
    }

    private void saveCounterValue() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(COUNTER_VALUE_KEY, counterValue);
        editor.apply();
    }

    private void saveCounterName() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(COUNTER_NAME_KEY, counterName);
        editor.apply();
    }
}
