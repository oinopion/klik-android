package eu.hauru.klik;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


public class CounterFragment extends Fragment {
    private static final String COUNTER_ID = "counterId";

    private CountersRepo repo;
    private Counter counter;
    private TextView counterNameView;
    private TextView counterValueView;


    public static CounterFragment newInstance(String counterId) {
        Bundle arguments = new Bundle();
        arguments.putString(COUNTER_ID, counterId);

        CounterFragment fragment = new CounterFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new CountersRepo();

        Bundle arguments = getArguments();
        counter = repo.getById(arguments.getString(COUNTER_ID));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_counter, container, false);

        counterValueView = (TextView) rootView.findViewById(R.id.counterValue);
        counterNameView = (TextView) rootView.findViewById(R.id.counterName);
        counter.addChangeListener((object, changeSet) -> render());

        Button counterIncrementButton = (Button) rootView.findViewById(R.id.counterIncrement);
        counterIncrementButton.setOnClickListener(this::onIncrementButtonClicked);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        render();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        counter.removeAllChangeListeners();
    }

    private void onIncrementButtonClicked(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        repo.incrementCounter(counter);
        renderCounterValue();
    }

    private void render() {
        renderCounterName();
        renderCounterValue();
    }

    private void renderCounterValue() {
        int value = counter.getValue();
        String paddedValue = String.format(Locale.ENGLISH, "%05d", value);
        counterValueView.setText(paddedValue);
    }

    private void renderCounterName() {
        if (counter.hasName()) {
            counterNameView.setText(counter.getName());
        } else {
            counterNameView.setText(R.string.default_counter_name);
        }
    }
}
