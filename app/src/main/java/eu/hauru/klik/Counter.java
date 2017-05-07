package eu.hauru.klik;


import android.text.TextUtils;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Counter extends RealmObject {
    @PrimaryKey
    @Required
    private String id;
    private Date createdAt = new Date();
    private int value = 0;
    private String name;

    public String getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public boolean hasName() {
        return !TextUtils.isEmpty(name);
    }

    void incrementValue() {
        value += 1;
    }

    void resetValue() {
        value = 0;
    }

    void setName(String name) {
        this.name = name.trim();
    }
}
