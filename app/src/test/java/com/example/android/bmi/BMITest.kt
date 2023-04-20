package com.example.android.bmi

import com.example.android.bmi.utilities.getBMI
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BMITest {

    @Test
    fun calculateBMI_enterWeightAndHeight_BMICalculatedCorrectly(){
        val height = "177"
        val weight = "84"
        val expectedBMI = "26.81"
        val actualBMI = getBMI(height, weight)
        assertEquals(expectedBMI, actualBMI)
    }
}