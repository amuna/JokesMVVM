package com.example.quotes.View.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quotes.Model.Joke;
import com.example.quotes.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.NoteHolder> {
    private List<Joke> jokes = new ArrayList<>();
    public int pos;
    private RecyclerViewClickListener listener;


    public JokeAdapter(RecyclerViewClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.joke_item, parent, false);
        return new NoteHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Joke currentJoke = jokes.get(position);

        holder.setup.setText(currentJoke.getSetup());
        holder.punchline.setText(String.valueOf(currentJoke.getPunchline()));
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    public void setJokes(List<Joke> notes) {
        this.jokes = notes;
        notifyDataSetChanged();
    }

    public Joke getJokeAt(int position) {
        return jokes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private ImageView favouriteStar;
        private TextView setup;
        private TextView punchline;

        public NoteHolder(@NonNull final View itemView, final RecyclerViewClickListener listener) {
            super(itemView);
            favouriteStar = itemView.findViewById(R.id.imageViewfavourite);
            setup = itemView.findViewById(R.id.textViewSetup);
            punchline = itemView.findViewById(R.id.textViewPunchline);

            favouriteStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onViewClicked(itemView, getAdapterPosition());
                    }
                }
            });


        }
    }
}
