package com.example.testapp.ui.GeneralUI

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomBlueButton(text: String, onClick: ()-> Unit, paddingValue: Int=10) {
    Column(
        Modifier
            .clickable(onClick=onClick)
            .background(Color(0xF9D4FDFF), shape = RoundedCornerShape(20.dp))
            //.border(4.dp,  Color(0xF9083880), shape = RoundedCornerShape(20.dp))
            .padding(paddingValue.dp), verticalArrangement = Arrangement.Center

    ) {
        Row {
            Text(
                text = text,
                color = Color(0xF9083880),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 0.dp),
                fontSize = 17.sp
            )
        }
    }
}