package eu.hauru.klik;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class CounterActivity extends Activity {
    final static String STATE_PREFERENCES = "state";

    private CounterState state;
    private TextView counterValueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        counterValueView = (TextView) findViewById(R.id.counterValue);

        restoreState();
        renderCounterValue();
    }

    public void incrementCounter(View view) {
        state.incrementValue();
        renderCounterValue();
    }

    private void restoreState() {
        SharedPreferences statePreferences = getSharedPreferences(STATE_PREFERENCES, MODE_PRIVATE);
        state = new CounterState(statePreferences);
    }

    private void renderCounterValue() {
        String paddedValue = String.format(Locale.ENGLISH, "%05d", state.getValue());
        counterValueView.setText(paddedValue);
    }
}
