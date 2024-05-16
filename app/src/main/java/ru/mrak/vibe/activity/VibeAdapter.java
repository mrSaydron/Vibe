package ru.mrak.vibe.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mrak.vibe.R;
import ru.mrak.vibe.model.VibeDto;

public class VibeAdapter extends RecyclerView.Adapter<VibeAdapter.VibeViewHolder> {

    private final List<VibeDto> vibes;

    public VibeAdapter(List<VibeDto> vibes) {
        this.vibes = vibes;
    }

    @NonNull
    @Override
    public VibeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vibe_card, parent, false);
        return new VibeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VibeViewHolder holder, int position) {
        VibeDto vibeDto = vibes.get(position);
        holder.textViewVibeTitle.setText(vibeDto.title);
        holder.textViewVibeDescription.setText(vibeDto.description);
        holder.textViewDateStart.setText(vibeDto.createDate.toString());
        holder.textViewDateEnd.setText(vibeDto.vibeEndDate.toString());
        holder.textViewUserCreate.setText(vibeDto.userCreateName);
    }

    @Override
    public int getItemCount() {
        if (vibes == null) {
            return 0;
        } else {
            return vibes.size();
        }
    }
    
    public static class VibeViewHolder extends RecyclerView.ViewHolder {

        TextView textViewVibeTitle;
        TextView textViewVibeDescription;
        TextView textViewDateStart;
        TextView textViewDateEnd;
        TextView textViewUserCreate;

        public VibeViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewVibeTitle = itemView.findViewById(R.id.textViewVibeTitle);
            textViewVibeDescription = itemView.findViewById(R.id.textViewVibeDescription);
            textViewDateStart = itemView.findViewById(R.id.textViewDateStart);
            textViewDateEnd = itemView.findViewById(R.id.textViewDateEnd);
            textViewUserCreate = itemView.findViewById(R.id.textViewUserCreate);
        }
    }
}
