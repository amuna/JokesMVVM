package com.example.quotes.ViewModel;

import com.example.quotes.Model.Joke;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface JokeAPI {

    @GET("random_joke")
    Observable<Joke> getSingleJoke();
}
