package eu.hauru.klik;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class CounterEditActivity extends AppCompatActivity {

    private CounterState state;

    private EditText counterNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_edit);
        counterNameView = (EditText) findViewById(R.id.counterNameEdit);

        state = CounterState.getInstance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (state.isNameSet()) {
            counterNameView.setText(state.getName());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateCounterName();
    }

    public void updateCounterName() {
        String editedName = counterNameView.getText().toString();
        state.setName(editedName);
    }

}
