package pl.edu.pwr.lab1mlynarczyk.lab1;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by mlyna on 26.03.2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    @Test
    public void displayNormalBMIWithCorrectKGData(){
        onView(withId(R.id.massEditText)).perform(typeText("73.5"));
        onView(withId(R.id.heightEditText)).perform(typeText("1.8"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.messageTextView)).check(matches(withText(containsString("22.69"))));
        onView(withId(R.id.opinionTextView)).check(matches(withText(R.string.normal)));

    }

    @Test
    public void displayNormalBMIWithCorrectLbData(){
        onView(withId(R.id.massEditText)).perform(typeText("120"));
        onView(withId(R.id.heightEditText)).perform(typeText("60"));
        onView(withId(R.id.kgSwitch)).perform(click());
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.messageTextView)).check(matches(withText(containsString("23.44"))));
        onView(withId(R.id.opinionTextView)).check(matches(withText(R.string.normal)));
    }

    @Test
    public void displayWrongInputWithIncorrectData(){
        onView(withId(R.id.massEditText)).perform(typeText("1"));
        onView(withId(R.id.heightEditText)).perform(typeText("1.8"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.messageTextView)).check(matches(withText(R.string.input_err)));
        onView(withId(R.id.opinionTextView)).check(matches(withText(containsString(""))));
    }

    @Test
    public void displayWrongInputWithEmptyField(){
        onView(withId(R.id.massEditText)).perform(typeText("73.5"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.messageTextView)).check(matches(withText(R.string.input_err)));
        onView(withId(R.id.opinionTextView)).check(matches(withText(containsString(""))));
    }

}
