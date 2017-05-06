package eu.hauru.klik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class CounterActivity extends AppCompatActivity {

    private CountersRepo repo;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        repo = new CountersRepo();
        pagerAdapter = new PagerAdapter();
        viewPager = (ViewPager) findViewById(R.id.counterPager);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_counter_menu, menu);
        return true;
    }

    public void onMenuAddClicked(MenuItem item) {
        Counter counter = repo.addCounter();
        viewPager.setCurrentItem(repo.getPosition(counter));
    }

    public void onMenuEditClicked(MenuItem item) {
        Intent intent = new Intent(this, CounterEditActivity.class);
        startActivity(intent);
    }

    public void onMenuResetClicked(MenuItem item) {
        AlertDialog dialog = buildResetDialog();
        dialog.show();
    }

    private AlertDialog buildResetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.reset_dialog_title);
        builder.setMessage(R.string.reset_dialog_message);
        builder.setPositiveButton(R.string.reset_dialog_ok_button, (dialog, which) -> {
        });
        builder.setNegativeButton(R.string.reset_dialog_cancel_button, null);

        return builder.create();
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        PagerAdapter() {
            super(getSupportFragmentManager());
            repo.addCountersChangeListener(this::notifyDataSetChanged);
        }

        @Override
        public Fragment getItem(int position) {
            Counter counter = repo.get(position);
            return CounterFragment.newInstance(counter.getId());
        }

        @Override
        public int getCount() {
            return repo.getSize();
        }
    }
}
