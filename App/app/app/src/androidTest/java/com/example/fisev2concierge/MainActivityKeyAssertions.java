package com.example.fisev2concierge;


import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityKeyAssertions {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.RECORD_AUDIO");

    @Test
    public void mainActivityKeyAssertions() {
        ViewInteraction button = onView(
                allOf(withId(R.id.reminders_view_button), withText("REMINDERS"),
                        withParent(allOf(withId(R.id.container),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.alarms_view_button), withText("ALARMS"),
                        withParent(allOf(withId(R.id.container),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.timers_view_button), withText("TIMERS"),
                        withParent(allOf(withId(R.id.container),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.help_view_button), withText("HELP"),
                        withParent(allOf(withId(R.id.container),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.tapToStartConciergeIcon), withContentDescription("Tap to start concierge icon"),
                        withParent(allOf(withId(R.id.container),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));
    }
}
