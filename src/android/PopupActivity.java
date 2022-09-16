package com.braintreepayments.popupbridge.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.util.Log;
import com.unibet.us_pa.R;

import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.api.PopupBridgeClient;

import org.apache.cordova.inappbrowser.IntentLogger;

public class PopupActivity extends AppCompatActivity {

    static final String BUNDLE_KEY_URL = "PopupActivity.BUNDLE_KEY_URL";

    private WebView mWebView;
    private PopupBridgeClient mPopupBridgeClient;
    private final static String TAG = "VENMODEMO.PopupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w( TAG, "onCreate(): ");
        setContentView(R.layout.activity_popup);
        mWebView = findViewById(R.id.web_view);

        mPopupBridgeClient = new PopupBridgeClient(this, mWebView, "com.braintreepayments.popupbridgeexample");
        mPopupBridgeClient.setErrorListener(error -> showDialog(error.getMessage()));

        String url = getIntent().getStringExtra(BUNDLE_KEY_URL);
        if (url == null) {
            // assume launch is from deep link; fetch url from persistent storage
            url = getPendingURLFromPersistentStorage();
        } else {
            Log.w( TAG, "getStringExtra: "+url);
        }

        mWebView.loadUrl(url);
        savePendingURL(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( TAG, "onResume(): ");
        if( mPopupBridgeClient != null) {
            IntentLogger.logFullContent( getIntent());
            mPopupBridgeClient.deliverPopupBridgeResult(this);
        } else {
            Log.w( TAG, "unexpected: mPopupBridgeClient is nul");
        }
    }

    @Override
    protected void onNewIntent(Intent newIntent) {
        super.onNewIntent(newIntent);
        Log.w( TAG, "onNewIntent(): ");
        setIntent(newIntent);
    }

    public void showDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    private String getPendingURLFromPersistentStorage() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String url = sharedPreferences.getString(BUNDLE_KEY_URL, null);
            Log.w( TAG, "getPendingURLFromPersistentStorage: "+url);
            return url;
        }
        Log.w( TAG, "getPendingURLFromPersistentStorage: null");
        return null;
    }

    private void savePendingURL(String url) {
        Log.w( TAG, "savePendingURL: "+url);
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            sharedPreferences.edit()
                    .putString(BUNDLE_KEY_URL, url)
                    .apply();
        }
    }
}
