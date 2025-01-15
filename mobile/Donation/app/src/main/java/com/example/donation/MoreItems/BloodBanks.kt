package com.example.donation.MoreItems

import android.annotation.SuppressLint
import android.os.Build
import android.util.Pair
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.ui.theme.dRed


data class HospitalAvailability(
    val name: String,
    val aPlus: Float,
    val bPlus: Float,
    val abPlus: Float,
    val abMinus: Float,
    val oPlus: Float,
    val oMinus: Float,
    val aMinus: Float,
    val bMinus: Float
)


@Composable
fun BloodBanks(navController: NavHostController) {
    var blood_selected by remember { mutableStateOf("") }
    var blood_expended by remember { mutableStateOf(false) }
    val blood_banks = listOf(
        "KMC Hospital",
        "Civil Hospital",
        "Bhaktapur Cancer Hospital"
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarTheme()
        CustomTopBar(img = Icons.Default.ArrowBack, greetingText = "", name = "", text = "Blood Banks", navController)

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            OutlinedTextField(
                value = blood_selected,
                onValueChange = { blood_selected = it },
                readOnly = true,
                label = { Text("Select Blood Bank") },
                trailingIcon = {
                    Icon(
                        imageVector = if (blood_expended) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = if (blood_expended) "Collapse Dropdown" else "Expand Dropdown",
                        modifier = Modifier.clickable { blood_expended = !blood_expended }
                    )
                },
                modifier = Modifier.fillMaxWidth(.9f)
                    .align(Alignment.TopCenter)
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { blood_expended = true }
                    .background(Color.Transparent)
            )
            DropdownMenu(
                expanded = blood_expended,
                onDismissRequest = { blood_expended = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                blood_banks.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            blood_selected = option
                            blood_expended = false
                        }
                    )
                }
            }

        }
        Box(
            modifier = Modifier.fillMaxWidth()
                .shadow(elevation = 20.dp),
            contentAlignment = Alignment.Center
        ){
            Spacer(modifier = Modifier.height(20.dp) )
            Text(text = " Bar chart will appear here")
            Spacer(modifier = Modifier.height(20.dp) )
        }
        OutlinedButton(
            onClick = {},

            modifier = Modifier
                .shadow(elevation = 40.dp),
            colors = ButtonDefaults.buttonColors(dRed),
            shape = RoundedCornerShape(10.dp)

        ) {
            Text(text = "Want to contribute? make schedule ")
        }




    }
}

//@Preview(showBackground = true)
@Composable
fun PShow(){
    val navController = rememberNavController()
    BloodBanks(navController)
}
@SuppressLint("RememberReturnType")
@Preview(showBackground = true)
@Composable
fun BarChart() {
    val hospitalData = listOf(
        HospitalAvailability(
            name = "KMC Hospital",
            aPlus = 2f,
            bPlus = 2f,
            abPlus = 2f,
            abMinus = 2f,
            oPlus = 2f,
            oMinus = 2f,
            aMinus = 2f,
            bMinus = 2f
        )
    )

    val chartDataHorizontal = listOf("A+", "B+", "AB+", "AB-", "O+", "O-", "A-", "B-")
    val spacingFromLeft = 100f
    val spacingFromBottom = 40f

    val density = LocalDensity.current
    val textPaint = remember(density) {
        android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
            .padding(15.dp)
    ) {
        val canvasHeight = size.height
        val canvasWidth = size.width

        val spacerData = (canvasWidth - spacingFromLeft) / chartDataHorizontal.size

        // Show horizontal data
        chartDataHorizontal.forEachIndexed { index, text ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    text,
                    spacingFromLeft + index * spacerData,
                    canvasHeight - spacingFromBottom / 2,
                    textPaint
                )
            }
        }

        //vertical data show
        val valuesToshow = 6f

        chartDataHorizontal.forEachIndexed { index,text ->
            drawContext.canvas.nativeCanvas.apply{
                drawText(
                    text,
                    60f,
                    spacingFromLeft + index * spacerData,
                    textPaint

                )
            }

        }

//        hospitalData.forEach { data ->
//            chartDataHorizontal.forEachIndexed { index, _ ->
//                drawRect(
//                    color = Color.Blue,
//                    topLeft = androidx.compose.ui.geometry.Offset(
//                        spacingFromLeft + index * spacerData,
//                        canvasHeight - spacingFromBottom - data.aPlus * 20f
//                    ),
//                    size = androidx.compose.ui.geometry.Size(20f, data.aPlus * 20f)
//                )
//            }
//        }
    }
}