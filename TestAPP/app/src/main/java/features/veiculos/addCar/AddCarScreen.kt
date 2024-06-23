package features.veiculos.addCar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.sharp.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapp.ui.theme.UserInfoGrey


@Composable
fun AddVehicleRoute(
    viewModel: AddVehicleViewModel = hiltViewModel(),
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit,
) {
    //val vehicleList = viewModel.vehicleListUiState.collectAsState()
    AddCarScreen(
        addingVehicleState = viewModel.addingVehicleState.collectAsState(initial = AddVehicleUIState.NotStarted),
        goToBackPage = goToBackPage,
        createNewVehicle = viewModel::createNewVehicle,
        matricula = viewModel.matricula,
        marca = viewModel.marca,
        modelo = viewModel.modelo,
        tipo = viewModel.tipo
    )
    hidTopBar()
}


@Composable
fun AddCarScreen(
    goToBackPage: () -> Unit,
    createNewVehicle: () -> Unit,
    matricula: MutableState<String>,
    marca: MutableState<String>,
    modelo: MutableState<String>,
    tipo: MutableState<String>,
    addingVehicleState: State<AddVehicleUIState>
) {


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

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {

                IconButton(onClick = goToBackPage) {

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "arrow to go back to user vehicle list ",
                        tint = Color.White,
                        modifier = Modifier
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(100)
                            )
                        //.clickable { }
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Adicionar Carro")

            }

            Column() {

                Text(
                    "Detalhes do Carro",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 30.dp, bottom = 20.dp, start = 20.dp)
                )

                Column(Modifier.padding(horizontal = 20.dp)) {
                    SimpleOutlinedTextFieldAddCar(
                        label = "Marca",
                        widthFraction = 0.85f,
                        text = marca
                    )
                    SimpleOutlinedTextFieldAddCar(
                        label = "Modelo",
                        widthFraction = 0.85f,
                        text = matricula
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SimpleOutlinedTextFieldAddCar(
                        label = "Matricula",
                        widthFraction = 0.4f,
                        text = modelo
                    )
                    ExposedDropdownMenuSample(text = tipo)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {

                AddButton(
                    text = "Adicionar Carro",
                    addingVehicleState = addingVehicleState,
                    createNewVehicle = createNewVehicle
                )
                if (addingVehicleState.value is AddVehicleUIState.Error) {
                    Row(Modifier.background(Color.Red, RoundedCornerShape(10.dp))) {
                        Text(
                            text = "NAO FOI POSSIVEL ADICIONAR CARRO POR FAVOR TENTE MAIS TARDE",
                            color = Color.White
                        )
                    }
                }
                if(addingVehicleState.value is AddVehicleUIState.Success){
                    Row(Modifier.background(Color.Green, RoundedCornerShape(10.dp))) {
                        Text(
                            text = "CARRO FOI ADICIONADO",
                            color = Color.White
                        )
                    }
                }
            }
        }


    }
}


@Composable
fun SimpleOutlinedTextFieldAddCar(
    label: String,
    widthFraction: Float = 1f,
    lastInput: Boolean = false,
    text: MutableState<String>
) {


    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(label) },
        shape = RoundedCornerShape(7.dp),
        modifier = Modifier//.fillMaxWidth(widthFraction)
            .width(LocalConfiguration.current.screenWidthDp.dp * widthFraction)
            .padding(vertical = 10.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = if (lastInput) ImeAction.Done else ImeAction.Next
        )
    )
}

/*
@Composable
fun SimpleDropDown() {

    val typeSelected = remember {
        mutableStateOf<VehicleTypes?>(null)
    }
    val state = rememberMenuState(expanded = false)

    @Composable
    fun ChevronDown(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "ChevronDown",
                defaultWidth = 16.dp,
                defaultHeight = 16.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
                path(
                    fill = null,
                    fillAlpha = 1.0f,
                    stroke = SolidColor(Color(0xFF000000)),
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 2f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(6f, 9f)
                    lineToRelative(6f, 6f)
                    lineToRelative(6f, -6f)
                }
            }.build()
        }
    }


    Box(
        Modifier
            .height(300.dp)
            .fillMaxWidth()
    ) {
        Menu(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .width(240.dp), state = state
        ) {
            MenuButton(
                Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .border(1.dp, Color(0xFFBDBDBD), RoundedCornerShape(6.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    if (typeSelected.value == null) {
                        BasicText(
                            "Selecionar Tipo Vehiculo",
                            style = TextStyle(fontWeight = FontWeight(500))
                        )
                    } else {
                        BasicText(
                            typeSelected.value!!.name,
                            style = TextStyle(fontWeight = FontWeight(500))
                        )
                    }
                    Spacer(Modifier.width(4.dp))
                    Image(ChevronDown(), null)
                }
            }

            MenuContent(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .width(320.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(6.dp))
                    .background(Color.White)
                    .padding(4.dp), hideTransition = fadeOut()
            ) {
                VehicleTypes.entries.forEach { vehicleType ->
                    MenuItem(
                        modifier = Modifier.clip(RoundedCornerShape(6.dp)),
                        onClick = { typeSelected.value = vehicleType }) {
                        BasicText(
                            vehicleType.name,
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp, horizontal = 10.dp)
                        )
                    }

                }
            }
        }
    }
}
*/

enum class VehicleTypes {
    Carro, Mota, Camiao, Carrinha,
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuSample(text: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    val widthFraction = 0.45f

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
    ) {
        OutlinedTextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/collapsing the menu on click. A read-only text field has
            // the anchor type `PrimaryNotEditable`.
            modifier = Modifier
                .menuAnchor()
                .width(LocalConfiguration.current.screenWidthDp.dp * widthFraction),//MenuAnchorType.PrimaryNotEditable),
            value = text.value,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text("tipo veiculo", fontSize = 14.sp) },
            trailingIcon = {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    null,
                    Modifier
                        .background(Color.White, CircleShape)
                        .rotate(if (expanded) 180f else 0f)
                )
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle.Default.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.White
            )
            //colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            VehicleTypes.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        text.value = option.name
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun AddButton(
    text: String,
    addingVehicleState: State<AddVehicleUIState>,
    createNewVehicle: () -> Unit
) {
    Column(
        Modifier
            .background(Color(0xFFA0EF9A), shape = RoundedCornerShape(15.dp))
            .clickable {createNewVehicle() }
            .padding(20.dp)
    ) {
        Row {
            if (addingVehicleState.value is AddVehicleUIState.Loading) {
                CircularProgressIndicator()
            } else {
                Icon(
                    Icons.Sharp.Create,
                    contentDescription = "",
                    tint = Color(0xFF005F02)
                )
                Text(
                    text = text,
                    color = Color(0xFF005F02),
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(start = 17.dp),
                    fontSize = 20.sp
                )
            }
        }
    }
}