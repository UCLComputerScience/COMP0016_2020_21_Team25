package com.example.fisev2concierge.UI;

import android.content.Intent;

import com.example.fisev2concierge.R;
import com.example.fisev2concierge.controllers.MainController;
import com.google.android.material.textfield.TextInputEditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class RegisterActivityTest {

    @Test
    public void backButtonTest() {
        RegisterActivity activity = Robolectric.buildActivity(RegisterActivity.class).create().get();
        activity.findViewById(R.id.backButtonRegister).performClick();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void correctValidationCodeTest() {
        RegisterActivity activity = Robolectric.buildActivity(RegisterActivity.class).create().get();
        TextInputEditText code1Input = activity.findViewById(R.id.registrationCodeText1);
        TextInputEditText code2Input = activity.findViewById(R.id.registrationCodeText2);
        TextInputEditText code3Input = activity.findViewById(R.id.registrationCodeText3);
        code1Input.setText("hello");
        code2Input.setText("there");
        code3Input.setText("world");
        activity.findViewById(R.id.submitRegistrationCode).performClick();
        MainController mainController = new MainController();
        assertTrue(mainController.hasUserID(activity));
        assertEquals("Successfully connected to admin!", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void incorrectValidationCodeTest() {
        RegisterActivity activity = Robolectric.buildActivity(RegisterActivity.class).create().get();
        TextInputEditText code1Input = activity.findViewById(R.id.registrationCodeText1);
        TextInputEditText code2Input = activity.findViewById(R.id.registrationCodeText2);
        TextInputEditText code3Input = activity.findViewById(R.id.registrationCodeText3);
        code1Input.setText("a");
        code2Input.setText("b");
        code3Input.setText("c");
        activity.findViewById(R.id.submitRegistrationCode).performClick();
        assertEquals("Incorrect validation codes", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void emptyValidationCodeTest() {
        RegisterActivity activity = Robolectric.buildActivity(RegisterActivity.class).create().get();
        activity.findViewById(R.id.submitRegistrationCode).performClick();
        assertEquals("Validation codes cannot be empty", ShadowToast.getTextOfLatestToast());
    }



}