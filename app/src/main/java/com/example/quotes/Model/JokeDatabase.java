package com.example.quotes.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Joke.class, version = 1)
public abstract class JokeDatabase extends RoomDatabase {
    private static JokeDatabase instance;

    public abstract JokeDao noteDao();

    public static synchronized JokeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    JokeDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
