package uk.ac.hope.mcse.android.coursework.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import uk.ac.hope.mcse.android.coursework.R;
import uk.ac.hope.mcse.android.coursework.model.MoodDatabase;
import uk.ac.hope.mcse.android.coursework.model.MoodEntry;

public class AddMoodFragment extends Fragment {

    private RadioGroup moodRadioGroup;
    private EditText noteEditText;

    public AddMoodFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_mood, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moodRadioGroup = view.findViewById(R.id.moodRadioGroup);
        noteEditText = view.findViewById(R.id.noteEditText);

        Button saveButton = view.findViewById(R.id.saveMoodButton);
        Button backButton = view.findViewById(R.id.backButton);

        saveButton.setOnClickListener(v -> {
            int selectedId = moodRadioGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(getContext(), "Please select a mood.", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedMood = view.findViewById(selectedId);
            String mood = selectedMood.getText().toString();
            String note = noteEditText.getText().toString();

            MoodEntry entry = new MoodEntry(mood, note, System.currentTimeMillis());
            MoodDatabase db = MoodDatabase.getInstance(requireContext());
            db.moodDao().insert(entry);

            Toast.makeText(getContext(), "Mood saved!", Toast.LENGTH_SHORT).show();

            moodRadioGroup.clearCheck();
            noteEditText.setText("");
        });

        backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
    }
}
