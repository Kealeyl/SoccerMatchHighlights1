package com.soccermatchhighlights;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MatchActions extends AppCompatActivity {
    private static final String TAG = MatchActions.class.getName();
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_match);
        webView = findViewById(R.id.web_view);
        Match match = (Match) getIntent().getSerializableExtra(Constants.ENTITY_SOCCER_MATCH);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(match.getTitle());
        Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show();
        webView.loadUrl(videoUrl(match.getVideos()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView = null;
    }

    public String videoUrl(List<Match.VideosDTO> videosDTO) {
        String res = "https://www.google.com/";
        if (videosDTO != null && !videosDTO.isEmpty()) {
            String[] embed = videosDTO.get(0).getEmbed().split(" ");
            for (int i = 0; i < embed.length; i++) {
                if (embed[i].contains("src=")) {
                    res = embed[i].substring(5, embed[i].length() - 1);
                    Log.i(TAG, "videoUrl: " + res);
                    break;
                }
            }
        }
        return res;
    }
}