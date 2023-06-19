package com.example.healthdigital;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AcceptanceTest1 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void acceptanceTest1() {
        ViewInteraction imageView = onView(
                allOf(withId(com.google.android.material.R.id.navigation_bar_item_icon_view),
                        withParent(allOf(withId(com.google.android.material.R.id.navigation_bar_item_icon_container),
                                withParent(allOf(withId(R.id.home), withContentDescription("Home"))))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(com.google.android.material.R.id.navigation_bar_item_large_label_view), withText("Home"),
                        withParent(allOf(withId(com.google.android.material.R.id.navigation_bar_item_labels_group),
                                withParent(allOf(withId(R.id.home), withContentDescription("Home"))))),
                        isDisplayed()));
        textView.check(matches(withText("Home")));

        ViewInteraction imageView2 = onView(
                allOf(withId(com.google.android.material.R.id.navigation_bar_item_icon_view),
                        withParent(allOf(withId(com.google.android.material.R.id.navigation_bar_item_icon_container),
                                withParent(allOf(withId(R.id.calendar), withContentDescription("Calendar"))))),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(com.google.android.material.R.id.navigation_bar_item_small_label_view), withText("Calendar"),
                        withParent(allOf(withId(com.google.android.material.R.id.navigation_bar_item_labels_group),
                                withParent(allOf(withId(R.id.calendar), withContentDescription("Calendar"))))),
                        isDisplayed()));
        textView2.check(matches(withText("Calendar")));

        ViewInteraction imageView3 = onView(
                allOf(withId(com.google.android.material.R.id.navigation_bar_item_icon_view),
                        withParent(allOf(withId(com.google.android.material.R.id.navigation_bar_item_icon_container),
                                withParent(allOf(withId(R.id.tasks), withContentDescription("Tasks"))))),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(com.google.android.material.R.id.navigation_bar_item_small_label_view), withText("Tasks"),
                        withParent(allOf(withId(com.google.android.material.R.id.navigation_bar_item_labels_group),
                                withParent(allOf(withId(R.id.tasks), withContentDescription("Tasks"))))),
                        isDisplayed()));
        textView3.check(matches(withText("Tasks")));

        ViewInteraction textView4 = onView(
                allOf(withId(com.google.android.material.R.id.navigation_bar_item_small_label_view), withText("Tasks"),
                        withParent(allOf(withId(com.google.android.material.R.id.navigation_bar_item_labels_group),
                                withParent(allOf(withId(R.id.tasks), withContentDescription("Tasks"))))),
                        isDisplayed()));
        textView4.check(matches(withText("Tasks")));
    }
}
