package com.example.quotes.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quotes.R;

import androidx.fragment.app.Fragment;


public class FavouriteFragment extends Fragment {
    private FragmentFavouriteListener fragmentFavouriteListener;

    public interface FragmentFavouriteListener{
        void onCompleteFavourite();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container,false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.fragmentFavouriteListener = (FragmentFavouriteListener) context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentFavouriteListener.onCompleteFavourite();
    }

}
