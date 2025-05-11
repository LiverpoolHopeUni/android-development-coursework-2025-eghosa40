package uk.ac.hope.mcse.android.coursework.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import uk.ac.hope.mcse.android.coursework.databinding.FragmentFirstBinding;
import uk.ac.hope.mcse.android.coursework.model.MoodDatabase;
import uk.ac.hope.mcse.android.coursework.model.MoodEntry;
import uk.ac.hope.mcse.android.coursework.R;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

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

        // View History button
        binding.viewHistoryButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.SecondFragment);
        });

        // Log Mood Button
        binding.logMoodButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.addMoodFragment);
        });


        // Show latest mood
        List<MoodEntry> entries = MoodDatabase.getInstance(requireContext())
                .moodDao().getAll();

        if (!entries.isEmpty()) {
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