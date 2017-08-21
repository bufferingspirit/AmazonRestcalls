package com.example.admin.amazonrestcalls;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FetchIntentService extends IntentService {
    public static final String BASE_URL = "https://de-coding-test.s3.amazonaws.com/books.json";
    public static final String TAG = "FetchIntentService";

    String resultResponse = "";
    Profile profile;

    public FetchIntentService() {
        super("FetchIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                resultResponse = response.body().string();
                Log.d(TAG, "onResponse: "  + resultResponse);
                Gson gson = new Gson();

                ArrayList<Profile> profileList = new Gson().fromJson(resultResponse, new TypeToken<ArrayList<Profile>>(){}.getType());
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                db.saveProfiles(profileList);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
