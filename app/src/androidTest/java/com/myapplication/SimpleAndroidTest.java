package com.myapplication;

import android.content.Context;
import android.test.AndroidTestCase;
import com.garagze.event.domain.SaleEvent;
import com.garagze.event.service.SaleEventManager;

import java.util.List;

/**
 * Created by jsangiorgio on 2/16/16.
 */

public class SimpleAndroidTest extends AndroidTestCase {

    public void testGetAllEvents() {
        Context context = getContext();
        List<SaleEvent> events =
                SaleEventManager.getAllEvents(context);
        assertTrue(events.size() > 0);
        assertEquals(15, events.size());
    }

}
