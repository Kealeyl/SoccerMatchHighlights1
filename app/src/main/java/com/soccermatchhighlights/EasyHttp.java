package com.soccermatchhighlights;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;

public class EasyHttp {
    private static EasyHttp easyHttp;
    private final static String soccerMatchURL = "https://www.scorebat.com/video-api/v1/";

    private EasyHttp() {
    }

    public static synchronized EasyHttp getInstance() {
        if (easyHttp == null) {
            easyHttp = new EasyHttp();
        }
        return easyHttp;
    }

    public void requestSoccerMatchList(Context context, HttpCallBack callBack) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, soccerMatchURL, callBack::onSuccess, error -> {
            if (error != null) {
                callBack.onError(500, error.getMessage());
            } else {
                callBack.onError(500, "error");
            }
        });
        queue.add(stringRequest);
    }
}
