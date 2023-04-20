package com.example.android.bmi.utilities

const val UNDERWEIGHT = "Underweight"
const val HEALTHY_WEIGHT = "Healthy weight"
const val OVERWEIGHT = "Overweight"
const val OBESITY = "Obesity"
const val UNKNOWN = "Unknown"


fun getBMI(heightInCm: String, weightInKg: String): String{
    // convert height to Meter
    val height = (heightInCm.toDoubleOrNull() ?: 0.0).div(100.00)
    val weight = weightInKg.toDoubleOrNull() ?: 0.0
    return if(height != 0.0){
        String.format("%.2f", weight.div(height.times(height)))
    }else{
        UNKNOWN
    }
}

fun getBodyStateByBMI(bodyMassIndex: Double) : String {
    return when(bodyMassIndex) {
        in(12.00..18.5) -> UNDERWEIGHT
        in(18.5..24.9) -> HEALTHY_WEIGHT
        in(24.90..29.9) -> OVERWEIGHT
        in(29.9..105.0) -> OBESITY
        else -> UNKNOWN
    }
}

fun getModerateWeightRange(heightInCm: String): String {
    val height = (heightInCm.toDoubleOrNull() ?: 0.0).div(100.00)
    val minWeight = 18.6.times(height.times(height))
    val maxWeight = 24.9.times(height.times(height))
    return "${String.format("%.1f", minWeight)}  ...  ${String.format("%.1f", maxWeight)}"

}
