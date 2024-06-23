package features.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResponseTransferPage(
    nProcesso: Int,
    closeResponsePage: () -> Unit,
    answerTransferRequest: (Boolean) -> Unit,
    reloadContraordDetalhesPage: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
            .clickable(onClick = closeResponsePage),
        Alignment.Center,

        ) {}

    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.Center,
    ) {
        Column(
            modifier = Modifier

                .background(Color(0xFF202547), shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(0.9f).padding(all=6.dp)
        ) {
            Text(
                "Responsabilidade sobre infração foi lhe atribuida quer aceitar",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { answerTransferRequest(true) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F8D2F)),
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Text(text = "ACEITAR")
                }

                Button(
                    onClick = { answerTransferRequest(false) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB10C0C))
                ) {
                    Text(text = "REJEITAR")
                }
            }
        }
    }
}