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
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ui.theme.dRed
import kotlin.math.round
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.donation.DataClasses.BloodRequest
import com.example.donation.DataClasses.OrganizationInventory
import com.example.donation.Navigation.Screens


@Composable
fun BloodBanks(navController: NavHostController,viewModel: SharedViewModel = viewModel()) {
    // Hospital data list
    //organization ko list haru ko lagi
    LaunchedEffect(Unit) {
        viewModel.fetchOrganizations()
    }
    val organizationList by viewModel.organizations.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchOrgData()
    }
    val bloodRequests by viewModel.inventory.collectAsState()

    var bloodSelected by remember { mutableStateOf("") }
    var bloodExpanded by remember { mutableStateOf(false) }
    var selectedHospital by remember { mutableStateOf<OrganizationInventory?>(null) }


    val handleDropDown = { name: String ->
        bloodSelected = name
        bloodExpanded = false
        selectedHospital = bloodRequests.find { it.organization_name == name }
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
                bloodRequests.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option.organization_name) },
                        onClick = { handleDropDown(option.organization_name) }
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
            onClick = {
                navController.navigate(Screens.schedultTime.route)
            },
            modifier = Modifier.shadow(elevation = 40.dp)
                .padding(start = 10.dp),
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
fun BarChart(hospitalData: OrganizationInventory) {
    val lowThreshold = 10f

    val chartData = listOf(
        Pair("A+", hospitalData.inventory.ABPlus),
        Pair("A-", hospitalData.inventory.AMinus),
        Pair("B+", hospitalData.inventory.BPlus),
        Pair("B-", hospitalData.inventory.BMinus),
        Pair("AB+", hospitalData.inventory.ABPlus),
        Pair("AB-", hospitalData.inventory.ABMinus),
        Pair("O+", hospitalData.inventory.OPlus),
        Pair("O-", hospitalData.inventory.OMinus)
    )



    val spacingFromLeft = 100f
    val spacingFromBottom = 50f
    val upperValue = remember { chartData.maxOfOrNull { it.second }?.plus(10) ?: 0f }.toFloat()

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

        // Draw horizontal labels
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

        // Vertical lines
        val valueToShow = 8f
        val eachStep = (upperValue.toFloat() - lowerValue) / valueToShow

        (0 until valueToShow.toInt()).forEach { i ->
            val label = lowerValue + eachStep * i
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(label).toString(),
                    20f,
                    canvasHeight - spacingFromBottom - i * (canvasHeight / valueToShow),
                    textPaint
                )
            }

            // Draw dash lines
            drawLine(
                start = Offset(spacingFromLeft - 20f, canvasHeight - spacingFromBottom - i * (canvasHeight / valueToShow)),
                end = Offset(spacingFromLeft, canvasHeight - spacingFromBottom - i * (canvasHeight / valueToShow)),
                color = Color.Black,
                strokeWidth = 3f
            )
        }

        // Draw x and y axes
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

        // Draw bars with correct positioning
        chartData.forEachIndexed { index, chartPair ->
            val barHeight = (chartPair.second / upperValue.toFloat()) * canvasHeight - (spacingFromBottom + 5f)
            val barTop = ((upperValue - chartPair.second)) / upperValue * canvasHeight

            // Set bar color based on threshold
            val barColor = when {
                chartPair.second < lowThreshold -> Color.Red
                else -> Color.Green
            }

            // Draw the bar
            drawRoundRect(
                color = barColor,
                topLeft = Offset(
                    spacingFromLeft + 10f + index * spacerData,
                    barTop
                ),
                size = Size(55f, barHeight),
                cornerRadius = CornerRadius(10f, 10f)
            )

            // Display value above the bar
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    chartPair.second.toString(),
                    spacingFromLeft + 40f + index * spacerData,
                    barTop - 10f,
                    textPaint
                )
            }
        }
    }

}

