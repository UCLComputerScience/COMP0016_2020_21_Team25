package com.example.fisev2concierge;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TimersKeyAssertions {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.RECORD_AUDIO");

    @Test
    public void timersKeyAssertions() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.timers_view_button), withText("Timers"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction editText = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.EditText.class), withText("0"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.EditText.class), withText("0"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

        ViewInteraction numberPicker = onView(
                allOf(withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        numberPicker.check(matches(isDisplayed()));

        ViewInteraction numberPicker2 = onView(
                allOf(withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        numberPicker2.check(matches(isDisplayed()));

        ViewInteraction numberPicker3 = onView(
                allOf(withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        numberPicker3.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.startTimerButton), withText("START"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.backButton), withText("BACK"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
