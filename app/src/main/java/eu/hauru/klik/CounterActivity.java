package eu.hauru.klik;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class CounterActivity extends AppCompatActivity {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_counter_menu, menu);
        return true;
    }

    public void incrementCounter(View view) {
        state.incrementValue();
        renderCounterValue();
    }

    public void resetCounter(MenuItem item) {

    }

    public void switchToEditActivity(MenuItem item) {
        Intent intent = new Intent(this, CounterEditActivity.class);
        startActivity(intent);
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
