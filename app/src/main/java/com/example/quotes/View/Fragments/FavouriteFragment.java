package com.example.quotes.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quotes.Model.Joke;
import com.example.quotes.R;
import com.example.quotes.View.Adapter.JokeAdapter;
import com.example.quotes.View.Adapter.RecyclerViewClickListener;
import com.example.quotes.ViewModel.JokeViewModel;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FavouriteFragment extends Fragment implements RecyclerViewClickListener {
    private RecyclerViewClickListener listener = this;
    private JokeAdapter adapter = new JokeAdapter(listener);
    private JokeViewModel jokeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);

        jokeViewModel = ViewModelProviders.of(this).get(JokeViewModel.class);

        jokeViewModel.getAllSavedJokes().observe(this, new Observer<List<Joke>>() {
            @Override
            public void onChanged(List<Joke> jokes) {
                adapter.setJokes(jokes);
            }
        });
        return view;
    }

    @Override
    public void onViewClicked(View v, int position) {
        jokeViewModel.deleteJoke(adapter.getJokeAt(position));
    }

}
