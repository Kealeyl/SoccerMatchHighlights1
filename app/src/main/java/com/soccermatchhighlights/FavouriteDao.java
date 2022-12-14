package com.soccermatchhighlights;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Query("SELECT * FROM favourite")
    List<Favourite> getAll();

    @Query("SELECT * FROM favourite WHERE soccerMatchJson = :soccerMatchJson")
    Favourite selectFavouriteBySoccerMatchJson(String soccerMatchJson);

    @Insert
    long insert(Favourite favourite);

    @Delete
    int delete(Favourite favourite);
}
