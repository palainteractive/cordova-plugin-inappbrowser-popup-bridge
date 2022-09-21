package com.braintreepayments.popupbridge.demo;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonToken;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.util.Log;

import com.braintreepayments.api.PopupBridgeNavigationListener;
import com.unibet.us_pa.R;

import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.api.PopupBridgeClient;

import org.apache.cordova.inappbrowser.IntentLogger;

import java.io.IOException;
import java.io.StringReader;

public class PopupActivity extends AppCompatActivity {

    private WebView mWebView;
    private PopupBridgeClient mPopupBridgeClient;
    private final static String TAG = "VENMODEMO.PopupActivity";

    private void areWeDone() {
        mWebView.evaluateJavascript("window.__venmo_payload", new ValueCallback<String>() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onReceiveValue(String s) {
                JsonReader reader = new JsonReader(new StringReader(s));

                // Must set lenient to parse single values
                reader.setLenient(true);

                try {
                    if (reader.peek() != JsonToken.NULL) {
                        if (reader.peek() == JsonToken.STRING) {
                            String msg = reader.nextString();
                            Log.w(TAG, "received payload: " + msg);
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "MainActivity: IOException", e);
                } finally {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        });
    }

    private void pollForResult() {
        (new Thread() {
            public void run() {
                try {
                    Thread.sleep(10000);
                    if (mWebView != null) {
                        areWeDone();
                    }
                } catch (InterruptedException e) {

                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate(): ");
        setContentView(R.layout.activity_popup);
        mWebView = findViewById(R.id.web_view);

        mPopupBridgeClient = new PopupBridgeClient(this, mWebView, "com.braintreepayments.popupbridgeexample");
        mPopupBridgeClient.setErrorListener(error -> showDialog(error.getMessage()));

//    this.pollForResult();
        mWebView.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume(): ");
        if (mPopupBridgeClient != null) {
            Intent i = getIntent();
            if (i.getDataString() != null) {
                Log.w(TAG, "delivering datastring to popupBridge: " + i.getDataString());
                mPopupBridgeClient.deliverPopupBridgeResult(this);
            } else {
                Log.w(TAG, "unexpected: intent datastring is null");
            }
        } else {
            Log.w(TAG, "unexpected: mPopupBridgeClient is nul");
        }
    }

    @Override
    protected void onNewIntent(Intent newIntent) {
        super.onNewIntent(newIntent);
        Log.w(TAG, "onNewIntent(): ");
        setIntent(newIntent);
    }

    public void showDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

}
