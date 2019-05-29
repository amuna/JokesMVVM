package com.example.quotes.View;

;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.quotes.View.Adapter.JokeAdapter;
import com.example.quotes.View.Adapter.RecyclerViewClickListener;
import com.example.quotes.View.Fragments.FavouriteFragment;
import com.example.quotes.View.Fragments.HomeFragment;
import com.example.quotes.Model.Joke;
import com.example.quotes.R;
import com.example.quotes.ViewModel.JokeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements HomeFragment.FragmentHomeListener, FavouriteFragment.FragmentFavouriteListener, RecyclerViewClickListener {
    private Fragment homeFragment;
    private Fragment favouriteFragment;

    private JokeViewModel jokeViewModel;
    private ImageView saveJokeBtn;
    private RelativeLayout loadingPanel;
    private boolean jokeSaved = false;
    private RelativeLayout jokePanel;

    private RecyclerViewClickListener listener = this;
    private JokeAdapter adapter = new JokeAdapter(listener);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init
        homeFragment = new HomeFragment();
        favouriteFragment = new FavouriteFragment();
        jokeViewModel = ViewModelProviders.of(this).get(JokeViewModel.class);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                homeFragment).commit();

        //observers
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
                ((HomeFragment) homeFragment).setJoke(jokeViewModel.getSingleJoke());
            }
        });

        jokeViewModel.getAllSavedJokes().observe(this, new Observer<List<Joke>>() {
            @Override
            public void onChanged(List<Joke> jokes) {
                adapter.setJokes(jokes);
            }
        });
    }

    //Functions handling user interaction
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = homeFragment;
                            break;
                        case R.id.nav_favourites:
                            selectedFragment = favouriteFragment;
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
                    return true;
                }
            };


    public void showNewJoke(View view) {
        jokeSaved = false;
        jokeViewModel.getSingleJokeAPI();
    }

    public void saveJoke(View view) {
        if (!jokeSaved) {
            saveJokeBtn.setImageResource(R.drawable.ic_star_yellow);
            jokeSaved = true;
            jokeViewModel.saveJoke(jokeViewModel.getSingleJoke());
            Toast.makeText(this, "Joke saved.", Toast.LENGTH_SHORT).show();
            return;
        }
        jokeSaved = false;
        saveJokeBtn.setImageResource(R.drawable.ic_star_border_black_24dp);
        jokeViewModel.deleteJoke(jokeViewModel.getSingleJoke());
        Toast.makeText(this, "Joke removed.", Toast.LENGTH_SHORT).show();
        return;
    }

    //deleting Joke from database
    @Override
    public void onViewClicked(View v, int position) {
        jokeViewModel.deleteJoke(adapter.getJokeAt(position));
    }

    //Interfaces to prevent race conditions between activity and fragment
    @Override
    public void onCompleteFavourite() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCompleteHome() {
        saveJokeBtn = (ImageView) findViewById(R.id.saveJokeBtn);
        loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);
        jokePanel = (RelativeLayout) findViewById(R.id.jokePanel);
        jokeSaved = false;
        showNewJoke(null);
    }
}
