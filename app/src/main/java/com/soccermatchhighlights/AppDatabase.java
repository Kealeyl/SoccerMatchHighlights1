package com.soccermatchhighlights;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favourite.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavouriteDao favouriteDao();
}
