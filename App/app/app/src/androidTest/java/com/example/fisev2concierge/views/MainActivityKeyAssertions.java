package com.example.fisev2concierge.views;


import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.fisev2concierge.R;

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
                    "android.permission.SEND_SMS",
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.READ_CONTACTS",
                    "android.permission.CALL_PHONE",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.INTERNET",
                    "android.permission.ACCESS_NETWORK_STATE",
                    "android.permission.WRITE_CONTACTS",
                    "android.permission.RECORD_AUDIO");

    @Test
    public void mainActivityKeyAssertions() {
        ViewInteraction imageView = onView(
                allOf(withId(R.id.tapToStartConciergeIcon), withContentDescription("Tap to start concierge icon"),
                        withParent(allOf(withId(R.id.container),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction scrollView = onView(
                allOf(withId(R.id.buttonsScrollView),
                        withParent(allOf(withId(R.id.container),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        scrollView.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.instructions_view_button), withText("INSTRUCTIONS"),
                        withParent(withParent(withId(R.id.buttonsScrollView))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.activity_register_button), withText("REGISTER"),
                        withParent(withParent(withId(R.id.buttonsScrollView))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.alarms_view_button), withText("ALARMS"),
                        withParent(withParent(withId(R.id.buttonsScrollView))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.reminders_view_button), withText("REMINDERS"),
                        withParent(withParent(withId(R.id.buttonsScrollView))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));
    }
}
