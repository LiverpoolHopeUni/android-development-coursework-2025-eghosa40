package uk.ac.hope.mcse.android.coursework.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoodDao {

    @Insert
    void insert(MoodEntry entry);

    @Query("SELECT * FROM MoodEntry ORDER BY timestamp DESC")
    List<MoodEntry> getAll();
}

