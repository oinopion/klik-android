package eu.hauru.klik;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class CounterActivity extends AppCompatActivity {

    private Repo repo;
    private TextView counterNameView;
    private TextView counterValueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        counterValueView = (TextView) findViewById(R.id.counterValue);
        counterNameView = (TextView) findViewById(R.id.counterName);

        repo = new Repo(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderCounterName();
        renderCounterValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_counter_menu, menu);
        return true;
    }

    public void incrementCounter(View view) {
        repo.incrementValue();
        renderCounterValue();
    }

    public void resetCounter() {
        repo.resetValue();
        renderCounterValue();
    }

    public void showResetDialog(MenuItem item) {
        AlertDialog dialog = prepareResetDialog();
        dialog.show();
    }

    public void switchToEditActivity(MenuItem item) {
        Intent intent = new Intent(this, CounterEditActivity.class);
        startActivity(intent);
    }

    private AlertDialog prepareResetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.reset_dialog_title);
        builder.setMessage(R.string.reset_dialog_message);
        builder.setPositiveButton(R.string.reset_dialog_ok_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetCounter();
                    }
                });
        builder.setNegativeButton(R.string.reset_dialog_cancel_button, null);

        return builder.create();
    }

    private void renderCounterValue() {
        String paddedValue = String.format(Locale.ENGLISH, "%05d", repo.getValue());
        counterValueView.setText(paddedValue);
    }

    private void renderCounterName() {
        String name = repo.getName();
        if (name != null) {
            counterNameView.setText(name);
        } else {
            counterNameView.setText(R.string.default_counter_name);
        }
    }

}
