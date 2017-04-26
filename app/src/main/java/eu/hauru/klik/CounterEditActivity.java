package eu.hauru.klik;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class CounterEditActivity extends AppCompatActivity {

    private Repo repo;

    private EditText counterNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_edit);
        counterNameView = (EditText) findViewById(R.id.counterNameEdit);

        repo = new Repo(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        counterNameView.setText(repo.getName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        setCounterName();
    }

    public void setCounterName() {
        String editedName = counterNameView.getText().toString();
        repo.setName(editedName);
    }

}
