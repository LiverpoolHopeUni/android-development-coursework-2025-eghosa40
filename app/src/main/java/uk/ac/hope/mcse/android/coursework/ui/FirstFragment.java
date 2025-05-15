package uk.ac.hope.mcse.android.coursework.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Collections;
import java.util.List;

import uk.ac.hope.mcse.android.coursework.databinding.FragmentFirstBinding;
import uk.ac.hope.mcse.android.coursework.model.MoodDatabase;
import uk.ac.hope.mcse.android.coursework.model.MoodEntry;
import uk.ac.hope.mcse.android.coursework.R;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private int selectedMoodId = -1;
    private long selectedTimestamp = 0;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle("");

        // Check if editing an existing mood
        Bundle args = getArguments();
        if (args != null) {
            int id = args.getInt("id", -1);
            String mood = args.getString("mood", "");
            String note = args.getString("note", "");
            long timestamp = args.getLong("timestamp", 0);

            if (id != -1) {
                selectedMoodId = id;
                selectedTimestamp = timestamp;
                binding.moodEditText.setText(mood);
                binding.noteEditText.setText(note);
            }
        }

        // Open mood history screen
        binding.viewHistoryButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.SecondFragment);
        });

        // Save mood (insert or update)
        binding.saveMoodButton.setOnClickListener(v -> {
            String mood = binding.moodEditText.getText().toString().trim();
            String note = binding.noteEditText.getText().toString().trim();

            if (mood.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a mood", Toast.LENGTH_SHORT).show();
                return;
            }

            MoodDatabase db = MoodDatabase.getInstance(requireContext());

            if (selectedMoodId != -1) {
                // Update existing mood
                MoodEntry updated = new MoodEntry(mood, note, selectedTimestamp);
                updated.id = selectedMoodId;
                db.moodDao().update(updated);
            } else {
                // Add new mood
                long currentTime = System.currentTimeMillis();
                MoodEntry newEntry = new MoodEntry(mood, note, currentTime);
                db.moodDao().insert(newEntry);
            }

            Navigation.findNavController(view).navigateUp();
        });

        // Show last logged mood
        List<MoodEntry> entries = MoodDatabase.getInstance(requireContext())
                .moodDao().getAll();

        if (!entries.isEmpty()) {
            Collections.sort(entries, (a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp()));
            MoodEntry latest = entries.get(0);
            String preview = latest.mood + " \"" + latest.note + "\"";
            binding.lastMoodTextView.setText("Last mood: " + preview);
        } else {
            binding.lastMoodTextView.setText("Last mood: (none yet)");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}