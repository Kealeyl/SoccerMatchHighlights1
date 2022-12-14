package com.soccermatchhighlights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.soccermatchhighlights.adapter.MatchAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActions extends AppCompatActivity {
    private static final String TAG = FavouriteActions.class.getName();
    private List<Match> listOfMatches = new ArrayList<>();
    private RecyclerView recyclerView;
    private MatchAdapter matchAdapter;
    private MutableLiveData<List<Favourite>> matchData;
    private List<Favourite> favorites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        recyclerView = findViewById(R.id.recycler_view);
        matchData = new MutableLiveData<List<Favourite>>();
        initRecyclerView();
        matchData.observe(this, new Observer<List<Favourite>>() {
            @Override
            public void onChanged(List<Favourite> favourites) {
                Log.i(TAG, " favourites size: " + favourites.size());
                favorites = favourites;
                listOfMatches.clear();
                Gson gson = new Gson();
                for (int i = 0; i < favourites.size(); i++) {
                    listOfMatches.add(gson.fromJson(favourites.get(i).soccerMatchJson, Match.class));
                }
                matchAdapter.setSoccerMatchListAndNotifyDataSetChanged(listOfMatches);
            }
        });
        AppDatabaseManager.getInstance(this).getAllFavourite(matchData);
    }

    private void initRecyclerView() {
        matchAdapter = new MatchAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(FavouriteActions.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(matchAdapter);
        matchAdapter.setClickEventListener(new MatchAdapter.ClickEvent() {
            @Override
            public void onClick(int position, Match match) {
                MutableLiveData<Integer> deleteLiveData = new MutableLiveData<>();
                AppDatabaseManager.getInstance(FavouriteActions.this).deleteFavourite(favorites.get(position), deleteLiveData);
                deleteLiveData.observe(FavouriteActions.this, new Observer() {
                    @Override
                    public void onChanged(Object o) {
                        Toast.makeText(FavouriteActions.this, R.string.success, Toast.LENGTH_SHORT).show();
                        AppDatabaseManager.getInstance(FavouriteActions.this).getAllFavourite(matchData);
                    }
                });
            }
        });
    }
}