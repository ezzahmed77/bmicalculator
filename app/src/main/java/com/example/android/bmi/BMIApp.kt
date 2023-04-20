package com.example.android.bmi

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.bmi.utilities.*


@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.calcuator),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun BMIScreen(
    modifier: Modifier = Modifier
) {
    var name by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }

    val bodyMassIndex = getBMI(height, weight)
    val state = getBodyStateByBMI(bodyMassIndex.toDoubleOrNull() ?: 0.0)
    val weightRange = getModerateWeightRange(height)

    val report = stringResource(id = R.string.report, name, height, weight, bodyMassIndex, state)
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    // color for state
    val color = when(state) {
        UNDERWEIGHT -> R.color.underweight
        HEALTHY_WEIGHT -> R.color.healthy_weight
        OVERWEIGHT -> R.color.overweight
        OBESITY -> R.color.obesity
        else -> R.color.black
    }
    Scaffold(topBar = { TopAppBar() } ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
                .wrapContentSize(align = Alignment.TopCenter)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            EditTextField(
                value = name,
                onValueChanged = {name = it},
                label = R.string.enter_your_name,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )

            EditTextField(
                value = height,
                onValueChanged = {height = it},
                label = R.string.enter_your_heigh_in_cm,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )

            EditTextField(
                value = weight,
                onValueChanged = {weight = it},
                label = R.string.enter_weight_in_kg,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions { focusManager.clearFocus() },
            )

            Spacer(modifier = Modifier.height(16.dp))

            ResultDetail(
                detailName = R.string.app_name,
                detailValue = bodyMassIndex
            )

            ResultDetail(
                detailName = R.string.state,
                detailValue = state,
                colorDetail = color
            )

            ResultDetail(
                detailName = R.string.weight_range,
                detailValue = weightRange
            )


            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { shareReport(report, context) },
                enabled = height.isNotEmpty() && weight.isNotEmpty() && name.isNotEmpty(),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(id = R.string.send_report),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

        }
    }

}

@Composable
fun EditTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text(
                text = stringResource(id = label)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )

}



@Composable
fun ResultDetail(
    modifier: Modifier = Modifier,
    @StringRes detailName: Int,
    detailValue: String,
    colorDetail: Int = R.color.black
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = detailName),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = detailValue,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = colorDetail),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

internal fun shareReport(
    report: String,
    context: Context
){
    val intent = Intent(Intent.ACTION_SEND)
    intent.apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Report")
        putExtra(Intent.EXTRA_TEXT, report)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            "Client Report"
        )
    )

}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun BMIScreenPreview() {
    BMIScreen()
}

@Composable
@Preview(showBackground = true)
fun EditTextFiledPreview() {
    EditTextField(value = "", onValueChanged = {}, label = R.string.app_name )
}

@Composable
@Preview(showBackground = true)
fun ResultDetailPreview() {
    ResultDetail(detailName = R.string.app_name, detailValue = "value")
}
