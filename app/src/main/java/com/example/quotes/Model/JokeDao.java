package com.example.quotes.Model;

import com.example.quotes.Model.Joke;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface JokeDao {

    @Insert
    void insert(Joke joke);

    @Delete
    void delete(Joke joke);

    @Query("SELECT * FROM joke_table")
    LiveData<List<Joke>> getAllSavedJokes();
}

