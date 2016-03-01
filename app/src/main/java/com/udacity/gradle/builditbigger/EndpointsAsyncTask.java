package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.brandnewpeterson.androidjokelib.JokeDisplayActivity;
import com.example.brandnewpeterson.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by brandnewpeterson on 2/25/16.
 */
class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private String EXTRA_JOKE = "joke";

    private static MyApi myApiService = null;
    private Activity activity;
    private Context context;
    private ProgressBar pb;
    //private MainActivity activity;


    public EndpointsAsyncTask(Activity activity, ProgressBar pb) {
        this.activity = activity;
        this.pb = pb;
    }

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];
        //String name = params[0].second;

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        pb.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(activity, JokeDisplayActivity.class);
        intent.putExtra(EXTRA_JOKE, result);
        activity.startActivity(intent);    }
}