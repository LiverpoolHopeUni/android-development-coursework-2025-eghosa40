package uk.ac.hope.mcse.android.coursework.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoodEntry {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String mood;
    public String note;
    public long timestamp;

    public MoodEntry(String mood, String note, long timestamp) {
        this.mood = mood;
        this.note = note;
        this.timestamp = timestamp;
    }

    public String getMood() {
        return mood;
    }

    public String getNote() {
        return note;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getId() {
        return id;
    }
}
