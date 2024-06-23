package features.veiculos.borrowCar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapp.ui.GeneralUI.SimpleOutlinedTextFieldSample
import com.example.testapp.ui.GeneralUI.TimePickerDialog
import com.example.testapp.ui.GeneralUI.getDateAndTimeFromComposeUI
import com.example.testapp.ui.theme.UserInfoGrey
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

@Composable
fun BorrowVehicleRoute(
    viewModel: BorrowVehicleViewModel = hiltViewModel(),
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit
) {
    hidTopBar()
    BorrowVehicleScreen(goToBackPage = goToBackPage, viewModel::loanVehicle)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrowVehicleScreen(
    goToBackPage: () -> Unit,
    makeLoanInvite: (String, LocalDateTime, LocalDateTime) -> Unit
) {
    val text = remember {
        mutableStateOf("")
    }
    val datePickerState = rememberDateRangePickerState()
    val showDatePicker = remember {
        mutableStateOf(false)
    }

    val startTimeState = rememberTimePickerState()

    val endTimeState = rememberTimePickerState()


    ProvideTextStyle(
        value = TextStyle(
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = UserInfoGrey),

            ) {}

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {

            IconButton(onClick = {}) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "arrow to go back to user vehicle list ",
                    tint = Color.White,
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(100)
                        )
                        .clickable { goToBackPage() }
                )
            }

            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Emprestar Carro")
        }
        Column(modifier = Modifier.padding(top = 30.dp)) {

            Text(
                "Detalhes Sobre Emprestimo do Carro",
                fontSize = 35.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 30.dp, bottom = 10.dp, start = 20.dp)
            )
            Box(modifier = Modifier.padding(vertical = 20.dp)) {
                DateTimeInputFields(
                    label = "DIA INICIAL",
                    showDatePicker = showDatePicker,
                    selectedDateMillis = datePickerState.selectedStartDateMillis,
                    timeState = startTimeState
                )
            }
            Box(modifier = Modifier.padding(vertical = 20.dp)) {
                DateTimeInputFields(
                    label = "DIA FINAL",
                    showDatePicker,
                    selectedDateMillis = datePickerState.selectedEndDateMillis,
                    timeState = endTimeState
                )
            }

            Column(Modifier.padding(start = 20.dp)) {
                Text(
                    text = "Escolher Utilizador",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                SimpleOutlinedTextFieldSample(
                    label = "UserID",
                    text = text,
                    widthFraction = 0.9f,
                    lastInput = true
                )

            }
            Box(
                Modifier
                    .padding(top = 50.dp, end = 20.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                BorrowButton(
                    text = "Emprestar"
                ) {
                    if (datePickerState.selectedStartDateMillis != null && datePickerState.selectedStartDateMillis != null) {
                        makeLoanInvite(
                            text.value,
                            getDateAndTimeFromComposeUI(
                                datePickerState.selectedStartDateMillis!!,
                                startTimeState.hour,
                                startTimeState.minute
                            ),
                            getDateAndTimeFromComposeUI(
                                datePickerState.selectedStartDateMillis!!,
                                startTimeState.hour,
                                startTimeState.minute
                            ),

                            )
                    }
                }
            }
        }

    }

    if (showDatePicker.value) {
        datePickerState.displayMode = DisplayMode.Picker
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                TextButton(
                    onClick = {},
                    enabled = true
                ) { Text(text = "Confirmar") }
            },
            dismissButton = { },
            content = { DateRangePicker(state = datePickerState) }
        )

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeInputFields(
    label: String,
    showDatePicker: MutableState<Boolean>,
    selectedDateMillis: Long?,
    timeState: TimePickerState
) {
    val showStartTimeInput = remember {
        mutableStateOf(false)
    }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround


        ) {
            Column(
                Modifier
                    .padding(end = 10.dp)
                    .clickable { showDatePicker.value = true }
                    .border(
                        if (selectedDateMillis != null) 2.dp else 1.dp,
                        if (selectedDateMillis != null) Color(0xFF5F4B98) else Color.LightGray,
                        shape = RoundedCornerShape(7.dp)
                    )
                    .padding(vertical = 15.dp)
                    .padding(end = 0.dp, start = 10.dp)
                    .fillMaxWidth(0.4f),

                //.fillMaxWidth(0.3f)
            ) {
                Text(
                    text = if (selectedDateMillis == null) label
                    else {
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        dateFormat.format(selectedDateMillis).toString()
                    },
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(onClick = { showStartTimeInput.value = true }) {
                var hour = timeState.hour.toString()
                var minute = timeState.minute.toString()
                if(timeState.hour < 10){
                    hour = "0${timeState.hour}"
                }
                if(timeState.minute < 10){
                    minute = "0${timeState.minute}"
                }
                Text("${hour} : ${minute}", fontSize = 35.sp)
            }
        }
    }

    if (showStartTimeInput.value) {
        TimePickerDialog(
            onCancel = { /*TODO*/ },
            onConfirm = { showStartTimeInput.value = false; }
        ) {
            TimeInput(state = timeState)
        }
    }
}


@Composable
fun PickedDateShower(
    label: String,
    widthFraction: Float = 1f,
    lastInput: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Decimal,
    text: String
) {
    //var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { },
        enabled = true,
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

@Composable
fun BorrowButton(text: String, makeLoanInvite: () -> Unit) {
    Column(
        Modifier
            .clickable(onClick = makeLoanInvite)
            .background(Color(0xFF9AECE2), shape = RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        Row {
            Icon(
                Icons.Sharp.Person,
                contentDescription = "",
                tint = Color(0xFF000E52)
            )
            Text(
                text = text,
                color = Color(0xFF000E52),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 20.dp),
                fontSize = 20.sp
            )
        }
    }
}