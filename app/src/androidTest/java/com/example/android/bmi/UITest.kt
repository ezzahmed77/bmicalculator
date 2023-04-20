package com.example.android.bmi

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.android.bmi.ui.theme.BMITheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp(){
        composeTestRule.setContent {
            BMITheme {
                BMIScreen()
            }
        }
    }

    @Test
    fun calculateBMI_enterAllData_assertAllDataRetrievedCorrectly(){
        // Enter all data
        composeTestRule.onNodeWithText("Enter your name")
            .performTextInput("Ahmed")
        composeTestRule.onNodeWithText("Enter height in (cm)")
            .performTextInput("177")
        composeTestRule.onNodeWithText("Enter weight (kg)")
            .performTextInput("84")

        // Make Sure BMI calculated
        composeTestRule.apply{
            onNodeWithText("26.81").assertExists()
            onNodeWithText("Overweight").assertExists()
            onNodeWithText("58.3  ...  78.0").assertExists()
            onNodeWithText("Send Report").assertIsEnabled()
        }
    }
}