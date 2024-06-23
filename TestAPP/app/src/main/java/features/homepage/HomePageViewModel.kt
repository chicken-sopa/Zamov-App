package features.homepage

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class HomePageViewModel : ViewModel() {

    //val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val drawerState = mutableStateOf(DrawerState(initialValue = DrawerValue.Closed))

    var selectedItemIndex by mutableIntStateOf(0)


    suspend fun changeDrawerState() {
            if (drawerState.value.isClosed) {
                drawerState.value.open()

            } else {
                drawerState.value.close()
            }

    }


}