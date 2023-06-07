package com.example.healthdigital;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        // Initialize Espresso Intents
        Intents.init();
    }

    @After
    public void cleanup() {
        // Release Espresso Intents
        Intents.release();
    }

    @Test
    public void testNavItemTasks_opensTaskViewFragmentEdit() {
        // Perform click on the "Tasks" item in the BottomNavigationView
        Espresso.onView(withId(R.id.tasks)).perform(click());

        // Verify that TaskViewFragmentEdit is opened
        Espresso.onView(withId(R.id.tasks)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavItemCalendar_opensCalendarFragment() {
        // Perform click on the "Calendar" item in the BottomNavigationView
        Espresso.onView(withId(R.id.calendar)).perform(click());

        // Verify that CalendarFragment is opened
        Espresso.onView(withId(R.id.calendar)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavItemHome_opensHomeFragment() {
        // Perform click on the "Home" item in the BottomNavigationView
        Espresso.onView(withId(R.id.home)).perform(click());

        // Verify that HomeFragment is opened
        Espresso.onView(withId(R.id.home)).check(matches(isDisplayed()));
    }
}

