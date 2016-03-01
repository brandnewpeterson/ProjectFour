package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.ProgressBar;

public class GCERetrieveTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Activity a;
    private ProgressBar pb;

    public GCERetrieveTest() {
        super(MainActivity.class);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();

        a = getActivity();
        pb = new ProgressBar(a.getApplicationContext());
    }

    public void testRetrieval() throws Exception {
        String s = new EndpointsAsyncTask(a, pb).execute(a.getApplicationContext()).get();
        Log.d("GCERetrieveTest", "Result is: " + s);
        assertTrue(s.length() > 0);

    }

}
