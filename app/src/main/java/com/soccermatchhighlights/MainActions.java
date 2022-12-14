package com.soccermatchhighlights;

import static com.soccermatchhighlights.Constants.ENTITY_SOCCER_MATCH;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soccermatchhighlights.adapter.MatchAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActions extends AppCompatActivity {
    private static final String TAG = MainActions.class.getName();
    private Banner banner;
    private BannerImageAdapter bannerImageAdapter;
    private List<Match> matchList = new ArrayList<>();
    private List<Match> bannerData = new ArrayList<>();
    private RecyclerView recyclerView;
    private MatchAdapter matchAdapter;
    private ImageView favouriteListIV, helpIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    /**
     *
     * @param
     */
    private void initView() {
        banner = findViewById(R.id.banner);
        recyclerView = findViewById(R.id.recycler_view);
        favouriteListIV = findViewById(R.id.img_favourite_list);
        helpIV = findViewById(R.id.img_help);
        banner.addBannerLifecycleObserver(this);
        initRecyclerView();
    }

    private void initData() {
        showToast(getString(R.string.loading));
        initBannerData();
        favouriteListIV.setOnClickListener(view -> {
            Intent intent = new Intent(MainActions.this, FavouriteActions.class);
            startActivity(intent);
        });
        helpIV.setOnClickListener(view -> {
            View alertView = LayoutInflater.from(this).inflate(R.layout.layout_help, null, false);
            AlertDialog alertDialog = new AlertDialog.Builder(this).setView(alertView).create();
            alertDialog.show();
        });
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    
    private void request() {
        EasyHttp.getInstance().requestSoccerMatchList(this, new HttpCallBack() {
            @SuppressLint("Notify")
            @Override
            public void onSuccess(String response) {
                showToast(getString(R.string.load_success));
                Gson gson = new Gson();
                List<Match> list = gson.fromJson(response, new TypeToken<ArrayList<Match>>() {
                }.getType());

                MutableLiveData<List<Favourite>> liveData = new MutableLiveData<List<Favourite>>();
                AppDatabaseManager.getInstance(MainActions.this).getAllFavourite(liveData);

                liveData.observe(MainActions.this, favourites -> {
                    Log.i(TAG, "data success");
                    for (int i = 0; i < favourites.size(); i++) {
                        Gson gson1 = new Gson();
                        Match matchFavourites = gson1.fromJson(favourites.get(i).soccerMatchJson, Match.class);
                        for (int j = 0; j < list.size(); j++) {
                            if (matchFavourites.getUrl().equals(list.get(j).getUrl())) {
                                list.get(j).setFavourite(true);
                                break;
                            }
                        }
                    }
                    if (list != null && !list.isEmpty()) {
                        Log.i(TAG, "on success -> list size:" + list.size());
                        matchList = list;
                        if (list.size() > 4) {
                            bannerData = list.subList(0, 3);
                            matchList = matchList.subList(3, matchList.size());
                        } else {
                            bannerData = list;
                        }
                        matchAdapter.setSoccerMatchListAndNotifyDataSetChanged(matchList);
                        bannerImageAdapter.setDatas(bannerData);
                        bannerImageAdapter.notifyDataSetChanged();
                    } else {
                        showToast(getString(R.string.parsing_failed_or_empty_data));
                        Log.e(TAG, " success -> " + "failure");
                    }
                });
            }

            @Override
            public void onError(int code, String msg) {
                showToast("error");
                Log.e(TAG, "error:" + code + ",msg:" + msg);
            }
        });
    }

    private void initBannerData() {
        bannerImageAdapter = new BannerImageAdapter<Match>(bannerData) {
            @Override
            public void onBindView(BannerImageHolder holder, Match match, int position, int size) {
                if (match != null) {
                    Glide.with(holder.itemView)
                            .load(match.getThumbnail())
                            .into(holder.imageView);
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActions.this, MatchActions.class);
                            intent.putExtra(ENTITY_SOCCER_MATCH, match);
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        banner.setAdapter(bannerImageAdapter);
    }

    private void initRecyclerView() {
        matchAdapter = new MatchAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActions.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(matchAdapter);

        matchAdapter.setClickEventListener((position, match) -> {
            if (!match.isFavourite()) {
                Gson gson = new Gson();
                Favourite favourite = new Favourite();
                match.setFavourite(true);
                favourite.soccerMatchJson = gson.toJson(match);
                AppDatabaseManager.getInstance(MainActions.this).installFavourite(favourite, new MutableLiveData<>());
                request();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        request();
    }
}