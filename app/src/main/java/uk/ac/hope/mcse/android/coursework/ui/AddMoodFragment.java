package uk.ac.hope.mcse.android.coursework.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import uk.ac.hope.mcse.android.coursework.R;
import uk.ac.hope.mcse.android.coursework.model.MoodDatabase;
import uk.ac.hope.mcse.android.coursework.model.MoodEntry;

public class AddMoodFragment extends Fragment {

    private static final String KEY_SELECTED_MOOD = "selected_mood";
    private static final String KEY_NOTE_TEXT = "note_text";

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

        saveButton.setEnabled(false);

        Runnable updateSaveButtonState = () -> {
            int selectedId = moodRadioGroup.getCheckedRadioButtonId();
            String noteText = noteEditText.getText().toString().trim();
            boolean isMoodSelected = selectedId != -1;
            boolean isNoteEntered = !noteText.isEmpty();
            saveButton.setEnabled(isMoodSelected || isNoteEntered);
        };

        moodRadioGroup.setOnCheckedChangeListener((group, checkedId) -> updateSaveButtonState.run());

        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSaveButtonState.run();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        saveButton.setOnClickListener(v -> {
            int selectedId = moodRadioGroup.getCheckedRadioButtonId();
            String note = noteEditText.getText().toString().trim();

            if (selectedId == -1 && note.isEmpty()) {
                Toast.makeText(getContext(), "Please select a mood or enter a note.", Toast.LENGTH_SHORT).show();
                return;
            }

            String mood = (selectedId != -1)
                    ? ((RadioButton) view.findViewById(selectedId)).getText().toString()
                    : "No Mood Selected";

            MoodEntry entry = new MoodEntry(mood, note, System.currentTimeMillis());
            MoodDatabase db = MoodDatabase.getInstance(requireContext());
            db.moodDao().insert(entry);

            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Mood Saved")
                    .setMessage("Your mood has been saved successfully.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();

            moodRadioGroup.clearCheck();
            noteEditText.setText("");
            saveButton.setEnabled(false);
        });

        backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });

        if (savedInstanceState != null) {
            int savedMoodId = savedInstanceState.getInt(KEY_SELECTED_MOOD, -1);
            String savedNote = savedInstanceState.getString(KEY_NOTE_TEXT, "");

            if (savedMoodId != -1) {
                moodRadioGroup.check(savedMoodId);
            }
            noteEditText.setText(savedNote);
            updateSaveButtonState.run();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_MOOD, moodRadioGroup.getCheckedRadioButtonId());
        outState.putString(KEY_NOTE_TEXT, noteEditText.getText().toString());
    }
}

