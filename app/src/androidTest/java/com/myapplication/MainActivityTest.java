package com.myapplication;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;

/**
 * Created by jsangiorgio on 2/16/16.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    Activity mainActivity;

    public MainActivityTest(Class<MainActivity> activityClass) {

        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        mainActivity = getActivity();
    }

    public void testActivityUI() {
        TextView textView = (TextView) mainActivity.findViewById(R.id.textView);
        assertEquals("Number of events: 15", textView.getText().toString());
    }

    @UiThreadTest
    public void testView() {
        TextView textView = (TextView) mainActivity.findViewById(R.id.textView);
        textView.setText("abc");
        assertEquals("abc", textView.getText().toString());
    }

}
