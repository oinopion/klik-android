package eu.hauru.klik;


import android.content.SharedPreferences;

class CounterState {
    final static String COUNTER_VALUE_KEY = "counterValue";

    private int counterValue;
    private final SharedPreferences preferences;

    public CounterState(SharedPreferences preferences) {
        super();
        this.preferences = preferences;
        this.counterValue = fetchCounterValue();
    }

    public int getValue() {
        return counterValue;
    }

    public void incrementValue() {
        counterValue += 1;
        saveCounterValue();
    }

    private int fetchCounterValue() {
        return preferences.getInt(COUNTER_VALUE_KEY, 0);
    }

    private void saveCounterValue() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(COUNTER_VALUE_KEY, counterValue);
        editor.apply();
    }
}
