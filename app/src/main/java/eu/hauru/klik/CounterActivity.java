package eu.hauru.klik;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {
    int counterValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
    }

    public void incrementCounter(View view) {
        counterValue += 1;
        String paddedValue = String.format("%05d", counterValue);

        TextView counterValueView = (TextView) findViewById(R.id.counterValue);
        counterValueView.setText(paddedValue);

    }
}
