package eu.hauru.klik;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CountersActivity extends AppCompatActivity {
    final static List<String> EMOJIS = Arrays.asList("🎉", "👻", "🍹", "👯", "🏀", "💰", "🚀");

    private CountersRepo repo;
    private ViewPager viewPager;
    private int currentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        repo = new CountersRepo();
        viewPager = (ViewPager) findViewById(R.id.counterPager);
        viewPager.setAdapter(new PagerAdapter());
        viewPager.addOnPageChangeListener(new PageSelectedListener());
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
        AlertDialog dialog = buildEditDialog(getCurrentCounter());
        dialog.show();
    }

    public void onMenuResetClicked(MenuItem item) {
        AlertDialog dialog = buildResetDialog(getCurrentCounter());
        dialog.show();
    }

    private AlertDialog buildEditDialog(final Counter counter) {
        View view = getLayoutInflater().inflate(R.layout.dialog_counter_edit, null);
        final EditText counterNameView = (EditText) view.findViewById(R.id.counterNameEdit);
        counterNameView.setText(counter.getName());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);
        builder.setTitle(R.string.edit_dialog_title);
        String message = getString(R.string.edit_dialog_message, getRandomEmoji());
        builder.setMessage(message);
        builder.setPositiveButton(R.string.edit_dialog_ok_button, (dialog, which) -> {
            String name = counterNameView.getText().toString();
            repo.setCounterName(counter, name);
        });
        builder.setNegativeButton(R.string.edit_dialog_cancel_button, null);
        return builder.create();
    }

    private AlertDialog buildResetDialog(final Counter counter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.reset_dialog_title);
        builder.setMessage(R.string.reset_dialog_message);
        builder.setPositiveButton(R.string.reset_dialog_ok_button, (dialog, which) -> {
            repo.resetCounter(counter);
        });
        builder.setNegativeButton(R.string.reset_dialog_cancel_button, null);

        return builder.create();
    }

    private Counter getCurrentCounter() {
        return repo.get(currentPage);
    }

    private String getRandomEmoji() {
        Random random = new Random();
        return EMOJIS.get(random.nextInt(EMOJIS.size()));
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

    class PageSelectedListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            currentPage = position;
        }
    }
}
