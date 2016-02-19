package com.myapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.garagze.event.domain.SaleEvent;
import com.garagze.event.service.SaleEventManager;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AddEventActivityTest {

    @Rule
    public ActivityTestRule<AddEventActivity> mActivityRule = new ActivityTestRule<>(AddEventActivity.class);
    @Test
    public void testAddEvent() {
        onView(withId(R.id.address)).perform(typeText("123 Main"));
        onView(withId(R.id.address)).check(matches(withText("123 Main")));
        onView(withId(R.id.addBtn)).perform(click());
        //Context context = InstrumentationRegistry.getContext();
        Context context = InstrumentationRegistry.getTargetContext();
        List<SaleEvent> events = SaleEventManager.getAllEvents(context);
        assertEquals(15, events.size());
    }
}
