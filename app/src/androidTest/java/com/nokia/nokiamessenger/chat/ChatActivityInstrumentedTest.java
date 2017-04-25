package com.nokia.nokiamessenger.chat;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nokia.nokiamessenger.R;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChatActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<ChatActivity> activityTestRule =
            new ActivityTestRule<>(ChatActivity.class);

    @Test
    public void inputMessage_shouldBeAddedToList() {
        Spoon.screenshot(activityTestRule.getActivity(), "without-input");
        onView(withId(R.id.chat_input)).perform(typeText("test-message"));

        Spoon.screenshot(activityTestRule.getActivity(), "with-input");
        onView(withId(R.id.chat_input_send)).perform(click());

        onView(withText("test-message")).check(matches(isDisplayed()));

        Spoon.screenshot(activityTestRule.getActivity(), "final-state");
    }
}
