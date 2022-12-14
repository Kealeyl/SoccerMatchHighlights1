package com.soccermatchhighlights;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favourite {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String soccerMatchJson;
}
