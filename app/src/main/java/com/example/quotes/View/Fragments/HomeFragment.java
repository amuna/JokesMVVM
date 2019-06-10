package com.example.quotes.View.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quotes.Model.Joke;
import com.example.quotes.R;
import com.example.quotes.ViewModel.JokeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment {
    private TextView textViewSingleJokeDisplay;

    private JokeViewModel jokeViewModel;
    private ImageView saveJokeBtn;
    private FloatingActionButton fabNewJoke;
    private RelativeLayout loadingPanel;
    private boolean jokeSaved = false;
    private RelativeLayout jokePanel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        loadingPanel = (RelativeLayout) view.findViewById(R.id.loadingPanel);
        jokePanel = (RelativeLayout) view.findViewById(R.id.jokePanel);
        textViewSingleJokeDisplay = (TextView) view.findViewById(R.id.textViewSingleJoke);

        fabNewJoke =(FloatingActionButton) view.findViewById(R.id.btnNewJoke);
        fabNewJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewJoke();
            }
        });

        saveJokeBtn = (ImageView) view.findViewById(R.id.saveJokeBtn);
        saveJokeBtn.bringToFront();
        saveJokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("IVCLICK", "YES");
                if (!jokeSaved) {
                    saveJokeBtn.setImageResource(R.drawable.ic_star_yellow);
                    jokeSaved = true;
                    jokeViewModel.saveJoke(jokeViewModel.getSingleJoke());
                    Toast.makeText(getContext(), "Joke saved.", Toast.LENGTH_SHORT).show();
                    return;
                }
                jokeSaved = false;
                saveJokeBtn.setImageResource(R.drawable.ic_star_border_black_24dp);
                jokeViewModel.deleteJoke(jokeViewModel.getSingleJoke());
                Toast.makeText(getContext(), "Joke removed.", Toast.LENGTH_SHORT).show();
            }
        });

        jokeViewModel = ViewModelProviders.of(this).get(JokeViewModel.class);
        jokeViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //Loading
                if (aBoolean) {
                    jokePanel.setVisibility(View.GONE);
                    loadingPanel.setVisibility(View.VISIBLE);
                    return;
                }
                loadingPanel.setVisibility(View.GONE);
                jokePanel.setVisibility(View.VISIBLE);
                setJoke(jokeViewModel.getSingleJoke());
            }
        });

        showNewJoke();
        return view;
    }

    public void showNewJoke() {
        jokeSaved = false;
        jokeViewModel.getSingleJokeAPI();
    }

    public void setJoke(Joke joke) {
        saveJokeBtn.setImageResource(R.drawable.ic_star_border_black_24dp);
        String content = "";
        content += joke.getSetup() + "\n\n";
        content += joke.getPunchline();
        textViewSingleJokeDisplay.setText(content);
    }
}
