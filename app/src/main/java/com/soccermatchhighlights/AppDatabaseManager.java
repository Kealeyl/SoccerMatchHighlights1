package com.soccermatchhighlights;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.google.gson.Gson;

import java.util.List;

public class AppDatabaseManager {
    private static AppDatabaseManager appDatabaseManager;
    private static final String databaseName = "soccer_match";
    private AppDatabase appDatabase;

    public static AppDatabaseManager getInstance(Context context) {
        if (appDatabaseManager == null) {
            appDatabaseManager = new AppDatabaseManager(context.getApplicationContext());
        }
        return appDatabaseManager;
    }

    private AppDatabaseManager(Context context) {
        appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, databaseName).build();
    }

    public void getAllFavourite(MutableLiveData<List<Favourite>> liveData) {
        GetAllFavouriteTask getAllFavouriteTask = new GetAllFavouriteTask(liveData);
        getAllFavouriteTask.start();
    }

    public void installFavourite(Favourite favourite, MutableLiveData<Long> liveData) {
        InsertFavouriteTask insertFavouriteTask = new InsertFavouriteTask(favourite, liveData);
        insertFavouriteTask.start();
    }

    public void deleteFavourite(Favourite favourite, MutableLiveData<Integer> liveData) {
        DeleteFavouriteTask getAllFavouriteTask = new DeleteFavouriteTask(favourite, liveData);
        getAllFavouriteTask.start();
    }

    private class GetAllFavouriteTask extends Thread {
        private MutableLiveData<List<Favourite>> listLiveData;

        public GetAllFavouriteTask(MutableLiveData<List<Favourite>> listLiveData) {
            this.listLiveData = listLiveData;
        }

        @Override
        public void run() {
            super.run();
            listLiveData.postValue(appDatabase.favouriteDao().getAll());
        }
    }

    private class InsertFavouriteTask extends Thread {
        private MutableLiveData<Long> liveData;
        private Favourite favourite;

        public InsertFavouriteTask(Favourite favourite, MutableLiveData<Long> liveData) {
            this.favourite = favourite;
            this.liveData = liveData;
        }

        @Override
        public void run() {
            super.run();
            liveData.postValue(appDatabase.favouriteDao().insert(favourite));
        }
    }

    private class DeleteFavouriteTask extends Thread {
        private MutableLiveData<Integer> liveData;
        private Favourite favourite;

        public DeleteFavouriteTask(Favourite favourite, MutableLiveData<Integer> liveData) {
            this.favourite = favourite;
            this.liveData = liveData;
        }

        @Override
        public void run() {
            super.run();
            liveData.postValue(appDatabase.favouriteDao().delete(favourite));
        }
    }
}
