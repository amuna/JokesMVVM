package com.example.quotes.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quotes.Model.Joke;
import com.example.quotes.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private TextView textViewSingleJokeDisplay;
    private ImageView imageViewStar;
    private FragmentHomeListener fragmentHomeListener;

    public interface FragmentHomeListener{
        void onCompleteHome();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container,false);
        textViewSingleJokeDisplay = (TextView) view.findViewById(R.id.textViewSingleJoke);
        imageViewStar = (ImageView) view.findViewById(R.id.saveJokeBtn);
        return view;
    }

    public void setJoke(Joke joke) {
        imageViewStar.setImageResource(R.drawable.ic_star_border_black_24dp);
        String content = "";
        content += joke.getSetup() + "\n\n";
        content += joke.getPunchline();
        textViewSingleJokeDisplay.setText(content);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.fragmentHomeListener = (FragmentHomeListener) context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentHomeListener.onCompleteHome();
    }
}
