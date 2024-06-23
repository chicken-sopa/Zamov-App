package features.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.data.User
import com.example.testapp.ui.theme.UserInfoGrey
import kotlin.reflect.full.memberProperties


@Composable
fun HomepageRoute(
    //viewModel: HomePageViewModel = hiltViewModel()
    showTopBar: () -> Unit,
) {
    showTopBar()
    Homepage()
    //LoginScreenRoute(showTopBar=showTopBar)
}


@Composable
fun Homepage() {

    var authentication by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(5.dp)

    ) {
        /*UserInfo()
        Spacer(modifier = Modifier.height(20.dp))
        BodyTitle()
        Spacer(modifier = Modifier.height(20.dp))
        HomePageButtons()*/
        ProvideTextStyle(
            value = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            ContraordenacaoInfo()
            Spacer(modifier = Modifier.height(20.dp))
            MyVehiclesInfo()
            Spacer(modifier = Modifier.height(20.dp))
            VehicleLoanToUserInfo()
        }
    }
}

@Composable
private fun UserInfo(userInfo: User? = null) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(UserInfoGrey, shape = RoundedCornerShape(20.dp))
            .safeContentPadding()
            .fillMaxWidth()
            .padding(all = 15.dp)

    ) {
        Text(text = "USER NAME", fontSize = 25.sp, color = Color.White)
        ProvideTextStyle(
            value = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "Nacionalidade: Portuguesa")
                    Text(text = "Pontos Carta: 12")
                    Text(text = "Licensa: L-8787878 8")
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "CC: 12345678")
                    Text(text = "Data Nasc: 20/10/24")
                }
            }
        }
    }
}

@Composable
fun ContraordenacaoInfo() {
    val test = TestClass(x = 1, y = 2, z=200)
    val fields = test.javaClass
    val members = TestClass::class.memberProperties.toMutableList()
    val pagerState = rememberPagerState(pageCount = { members.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(color=Color.White, shape = RoundedCornerShape(5))
            .padding(horizontal = 25.dp, vertical = 15.dp)
            .fillMaxWidth(0.95f)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Text(text = "Contraordenções", fontSize = 20.sp)
            DotIndicators(pageCount = pagerState.pageCount, pagerState=pagerState)
        }
        Column {
            HorizontalPager(
                //pageCount = animals.size,
                state = pagerState,
                key = { members[it].name },
                pageSize = PageSize.Fill
            ) {


                //Text(text="${it.name} --> ${it.get(test)} --> ${TestClass.description[it.name]}")
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth()
                ) {
                    Text("${members[it].get(test)}", fontSize = 45.sp)
                    Text("${TestClass.description[members[it].name]}", fontSize = 20.sp, fontWeight = FontWeight.Light)
                }
            }
        }
    }
}

@Composable
fun MyVehiclesInfo() {

    Column(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(5))
            .padding(horizontal = 30.dp, vertical = 20.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(text = "Os Meus Carros", fontSize = 30.sp)
        }
        Column {
            Text(
                text = """
            Carros Registados: 3 
            Carros Emprestados: 2
            Total Dividas Pendentes : 200€
            Convites A Espera de Resposta: 1
        """.trimIndent()
            )
        }
    }

}

@Composable
fun VehicleLoanToUserInfo() {
    Column(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(5))
            .padding(horizontal = 30.dp, vertical = 20.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(text = "Carros Emprestados Por Outrem", fontSize = 30.sp)
        }
        Column {
            Text(
                text = """
            Carros Registados: 3 
            Carros Emprestados: 2
            Total Dividas Pendentes : 200€
            Convites A Espera de Resposta: 1
        """.trimIndent()
            )
        }
    }
}


@Composable
fun DotIndicators(
    pageCount: Int,
    pagerState: PagerState,
    //modifier: Modifier
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.LightGray else Color.DarkGray
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(10.dp)

            )
        }

    }
}


@Composable
@Preview
fun HomePagePreview() {
    Homepage()
}


class TestClass(val x: Int, val y: Int, val z: Int) {
    companion object {
        val description = mapOf(
            "x" to "contraordenações expiradas",
            "y" to "esta feito",
            "z" to "dividas pendentes"
        )

    }
}
