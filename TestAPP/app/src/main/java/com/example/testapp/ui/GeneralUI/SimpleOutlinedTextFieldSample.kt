package com.example.testapp.ui.GeneralUI

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SimpleOutlinedTextFieldSample(
    label: String,
    widthFraction: Float = 1f,
    lastInput: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Decimal,
    text: MutableState<String>
) {
    //var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(label) },
        shape = RoundedCornerShape(7.dp),
        modifier = Modifier//.fillMaxWidth(widthFraction)
            .width(LocalConfiguration.current.screenWidthDp.dp * widthFraction),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = if (lastInput) ImeAction.Done else ImeAction.Next
        )
    )
}