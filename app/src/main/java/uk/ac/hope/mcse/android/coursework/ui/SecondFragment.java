package uk.ac.hope.mcse.android.coursework.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.hope.mcse.android.coursework.R;
import uk.ac.hope.mcse.android.coursework.adapter.MoodAdapter;
import uk.ac.hope.mcse.android.coursework.model.MoodDatabase;
import uk.ac.hope.mcse.android.coursework.model.MoodEntry;

public class SecondFragment extends Fragment {

    private RecyclerView recyclerView;
    private MoodAdapter adapter;

    public SecondFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mood_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.moodRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        List<MoodEntry> moodList = MoodDatabase
                .getInstance(requireContext())
                .moodDao()
                .getAll();

        adapter = new MoodAdapter(moodList);
        recyclerView.setAdapter(adapter);
    }
}


