package com.soccermatchhighlights;

public interface HttpCallBack {
    void onSuccess(String response);

    void onError(int code, String msg);
}
