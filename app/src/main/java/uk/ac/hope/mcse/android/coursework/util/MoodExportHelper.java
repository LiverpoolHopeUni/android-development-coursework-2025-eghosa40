package uk.ac.hope.mcse.android.coursework.util;

import android.content.Context;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import uk.ac.hope.mcse.android.coursework.model.MoodEntry;

public class MoodExportHelper {

    public static void exportMoodsToFile(Context context, List<MoodEntry> moods) {
        String fileName = "mood_logs.txt";
        StringBuilder data = new StringBuilder();

        for (MoodEntry mood : moods) {
            String dateString = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
                    .format(new java.util.Date(mood.getTimestamp()));

            data.append("Date: ").append(dateString)
                    .append("\nMood: ").append(mood.getMood())
                    .append("\nNote: ").append(mood.getNote())
                    .append("\n------------------\n");
        }

        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(data.toString().getBytes());
            Toast.makeText(context, "Mood logs exported to " + fileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Export failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}