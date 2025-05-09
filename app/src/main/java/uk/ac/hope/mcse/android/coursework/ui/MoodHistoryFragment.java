package uk.ac.hope.mcse.android.coursework.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.hope.mcse.android.coursework.R;
import uk.ac.hope.mcse.android.coursework.adapter.MoodAdapter;
import uk.ac.hope.mcse.android.coursework.model.MoodDatabase;
import uk.ac.hope.mcse.android.coursework.model.MoodEntry;

public class MoodHistoryFragment extends Fragment {

    public MoodHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mood_history, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.moodRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch mood entries from Room DB
        List<MoodEntry> moodList = MoodDatabase
                .getInstance(requireContext())
                .moodDao()
                .getAll();

        // Hook data into adapter
        MoodAdapter adapter = new MoodAdapter(moodList);
        recyclerView.setAdapter(adapter);
    }
}

