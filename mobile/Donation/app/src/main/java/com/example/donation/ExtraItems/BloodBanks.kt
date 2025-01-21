package com.example.donation.ExtraItems

import android.annotation.SuppressLint
import android.util.Pair
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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
import kotlin.math.round

// dummy data
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
    // Hospital data list
    val hospitalData = listOf(
        HospitalAvailability("KMC Hospital", 5f, 3f, 12f, 21f, 29f, 42f, 43f, 56f),
        HospitalAvailability("Civil Hospital", 35f, 13f, 11f, 24f, 9f, 44f, 11f, 6f),
        HospitalAvailability("Cancer Hospital", 23f, 33f, 52f, 14f, 22f, 7f, 13f, 29f)
    )

    var bloodSelected by remember { mutableStateOf("") }
    var bloodExpanded by remember { mutableStateOf(false) }
    var selectedHospital by remember { mutableStateOf<HospitalAvailability?>(null) }


    val handleDropDown = { name: String ->
        bloodSelected = name
        bloodExpanded = false
        selectedHospital = hospitalData.find { it.name == name }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TopBarTheme()
        // Top bar
        CustomTopBar(img = Icons.Default.ArrowBack, greetingText = "", name = "", text = "Blood Banks", navController)

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            OutlinedTextField(
                value = bloodSelected,
                onValueChange = { bloodSelected = it },
                readOnly = true,
                label = { Text("Select Blood Bank") },
                trailingIcon = {
                    Icon(
                        imageVector = if (bloodExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = if (bloodExpanded) "Collapse Dropdown" else "Expand Dropdown",
                        modifier = Modifier.clickable { bloodExpanded = !bloodExpanded }
                    )
                },
                modifier = Modifier.fillMaxWidth(0.9f)
                    .align(Alignment.TopCenter)
            )

            Box(modifier = Modifier.matchParentSize().clickable { bloodExpanded = true }.background(Color.Transparent))

            DropdownMenu(
                expanded = bloodExpanded,
                onDismissRequest = { bloodExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                hospitalData.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option.name) },
                        onClick = { handleDropDown(option.name) }
                    )
                }
            }
        }


        selectedHospital?.let { hospital ->
            Box(modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
                contentAlignment = Alignment.Center,

                    ) {
                Spacer(modifier = Modifier.height(20.dp))
                BarChart(hospital)
            }
        }


        // Schedule button
        OutlinedButton(
            onClick = {},
            modifier = Modifier.shadow(elevation = 40.dp),
            colors = ButtonDefaults.buttonColors(dRed),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Want to contribute? Make a schedule")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBloodBanks() {
    val navController = rememberNavController()
    BloodBanks(navController)
}



@SuppressLint("RememberReturnType")
@Composable
fun BarChart(hospitalData: HospitalAvailability) {
    val lowThreshold = 10f

    val chartData = listOf(
        Pair("A+", hospitalData.aPlus),
        Pair("A-", hospitalData.aMinus),
        Pair("B+", hospitalData.bPlus),
        Pair("B-", hospitalData.bMinus),
        Pair("AB+", hospitalData.abPlus),
        Pair("AB-", hospitalData.abMinus),
        Pair("O+", hospitalData.oPlus),
        Pair("O-", hospitalData.oMinus)
    )



    val spacingFromLeft = 100f
    val spacingFromBottom = 50f
    val upperValue = remember { chartData.maxOfOrNull { it.second }?.plus(10) ?: 0f }
    val lowerValue = remember { 0f }


    val density = LocalDensity.current
    val textPaint = remember(density) {
        android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = android.graphics.Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.White)
            .padding(15.dp)
    ) {
        val canvasHeight = size.height
        val canvasWidth = size.width
        val spacerData = (canvasWidth - spacingFromLeft) / chartData.size

        // horizontal value
        chartData.forEachIndexed { index, pair ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    pair.first,
                    spacingFromLeft + 50f + index * spacerData,
                    canvasHeight - spacingFromBottom / 20,
                    textPaint
                )
            }
        }

        // vertical line
        val valueToShow = 8f
        val eachStep = (upperValue - lowerValue) / valueToShow

        (0 until valueToShow.toInt()).forEach { i ->
            val label = lowerValue + eachStep * i
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(label).toString(),
                    20f,
                    canvasHeight - 30f - i * canvasHeight / 8f,
                    textPaint
                )
            }

            // dash line haru
            drawLine(
                start = Offset(spacingFromLeft - 20f, canvasHeight - spacingFromBottom - i * canvasHeight / 8f),
                end = Offset(spacingFromLeft, canvasHeight - spacingFromBottom - i * canvasHeight / 8f),
                color = Color.Black,
                strokeWidth = 3f
            )
        }

        //  x rw y axis
        drawLine(
            start = Offset(spacingFromLeft, canvasHeight - spacingFromBottom),
            end = Offset(spacingFromLeft, 0f),
            color = Color.Black,
            strokeWidth = 3f
        )

        drawLine(
            start = Offset(spacingFromLeft, canvasHeight - spacingFromBottom),
            end = Offset(canvasWidth - 50f, canvasHeight - spacingFromBottom),
            color = Color.Black,
            strokeWidth = 3f
        )

        // Draw bars
        chartData.forEachIndexed { index, chartPair ->

            val barColor = when{
                chartPair.second < lowThreshold -> Color.Red
                else -> Color.Green
            }
            drawRoundRect(
                color = barColor,
                topLeft = Offset(
                    spacingFromLeft + 10f + index * spacerData,
                    (upperValue - chartPair.second) / upperValue * canvasHeight
                ),
                size = Size(55f, (chartPair.second / upperValue) * canvasHeight-(spacingFromBottom+5f)),
                cornerRadius = CornerRadius(10f, 10f),

            )

            //top ma value display garna laii
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    chartPair.second.toString(),
                    spacingFromLeft + 40f + index * spacerData,
                    (upperValue - chartPair.second) / upperValue * canvasHeight - 10f,
                    textPaint
                )
            }
        }
    }
}
