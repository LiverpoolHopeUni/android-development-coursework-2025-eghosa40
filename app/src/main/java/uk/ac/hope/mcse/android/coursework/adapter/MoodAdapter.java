package uk.ac.hope.mcse.android.coursework.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.ac.hope.mcse.android.coursework.R;
import uk.ac.hope.mcse.android.coursework.model.MoodEntry;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MoodViewHolder> {

    private List<MoodEntry> moodList;

    private OnItemClickListener listener;

    public List<MoodEntry> getMoodList() {
        return moodList;
    }

    public MoodAdapter(List<MoodEntry> moodList) {
        this.moodList = moodList;
    }

    @NonNull
    @Override
    public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mood, parent, false);
        return new MoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoodViewHolder holder, int position) {
        MoodEntry mood = moodList.get(position);
        holder.moodTextView.setText(mood.mood);
        holder.noteTextView.setText(mood.note);

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date(mood.timestamp));
        holder.timestampTextView.setText(date);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(moodList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return moodList.size();
    }

    public static class MoodViewHolder extends RecyclerView.ViewHolder {
        TextView moodTextView, noteTextView, timestampTextView;

        public MoodViewHolder(@NonNull View itemView) {
            super(itemView);
            moodTextView = itemView.findViewById(R.id.moodTextView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(MoodEntry moodEntry);
    }
}

