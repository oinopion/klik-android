package eu.hauru.klik;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class CounterActivity extends Activity {
    private int counterValue = 0;

    private TextView counterValueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        counterValueView = (TextView) findViewById(R.id.counterValue);
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderCounterValue();
    }

    public void incrementCounter(View view) {
        counterValue += 1;
        renderCounterValue();
    }

    private void renderCounterValue() {
        final String paddedValue = String.format(Locale.ENGLISH, "%05d", counterValue);
        counterValueView.setText(paddedValue);
    }
}
