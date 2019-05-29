package com.example.quotes.ViewModel;

import android.app.Application;
import android.util.Log;

import com.example.quotes.Model.Joke;
import com.example.quotes.Model.JokeDao;
import com.example.quotes.Model.JokeDatabase;
import com.example.quotes.ViewModel.JokeAPI;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokeViewModel extends AndroidViewModel {
    public static final String TAG = "Database operation";
    private JokeDao jokeDao;
    private Retrofit retrofit;
    private JokeAPI jokeAPI;

    private Joke singleJoke;
    private LiveData<List<Joke>> allSavedJokes;

    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public JokeViewModel(@NonNull Application application) {
        super(application);
        JokeDatabase database = JokeDatabase.getInstance(application);
        jokeDao = database.noteDao();
        allSavedJokes = jokeDao.getAllSavedJokes();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://official-joke-api.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        jokeAPI = retrofit.create(JokeAPI.class);
    }

    public void saveJoke(final Joke joke) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                jokeDao.insert(joke);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Joke Insert Successful");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Joke Insert Error: " + e.getMessage());
            }
        });
    }

    public void deleteJoke(final Joke joke) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                jokeDao.delete(joke);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Joke Delete Successful");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Joke Delete Error: " + e.getMessage());
            }
        });
    }

    public void getSingleJokeAPI() {
        loading.setValue(true);
        final Observable<Joke> singleJokeObservable = jokeAPI.getSingleJoke();
        singleJokeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Joke>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Joke joke) {
                        singleJoke = joke;

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        loading.setValue(false);
                    }
                });
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void setLoading(MutableLiveData<Boolean> loading) {
        this.loading = loading;
    }

    public Joke getSingleJoke() {
        return singleJoke;
    }

    public void setSingleJoke(Joke singleJoke) {
        this.singleJoke = singleJoke;
    }

    public LiveData<List<Joke>> getAllSavedJokes() {
        return allSavedJokes;
    }

    public void setAllSavedJokes(LiveData<List<Joke>> allSavedJokes) {
        this.allSavedJokes = allSavedJokes;
    }
}
