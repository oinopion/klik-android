package eu.hauru.klik;


import java.util.Objects;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class CountersRepo {

    public interface OnCountersChangeListener {
        void onCountersChange();
    }

    private Realm realm;
    private RealmResults<Counter> counters;

    public CountersRepo() {
        realm = Realm.getDefaultInstance();
        counters = queryAllCounters();
        if (counters.isEmpty()) {
            addCounter();
        }
    }

    public int getSize() {
        return counters.size();
    }

    public Counter get(int position) {
        return counters.get(position);
    }

    public Counter getById(String id) {
        for (Counter counter : counters) {
            if (Objects.equals(counter.getId(), id)) {
                return counter;
            }
        }

        throw new RuntimeException("Could not find counter with id " + id);
    }

    public int getPosition(Counter counter) {
        return counters.indexOf(counter);
    }

    public Counter addCounter() {
        realm.beginTransaction();
        Counter counter = realm.createObject(Counter.class, UUID.randomUUID().toString());
        realm.commitTransaction();
        return counter;
    }

    public void incrementCounter(Counter counter) {
        realm.beginTransaction();
        counter.incrementValue();
        realm.commitTransaction();
    }

    public void addCountersChangeListener(OnCountersChangeListener listener) {
        counters.addChangeListener((collection, changeSet) -> listener.onCountersChange());
    }

    private RealmResults<Counter> queryAllCounters() {
        return realm.where(Counter.class).findAllSorted("createdAt");
    }


}
