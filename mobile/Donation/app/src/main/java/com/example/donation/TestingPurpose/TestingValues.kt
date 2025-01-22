package com.example.donation.TestingPurpose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.donation.backend.RegViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview(showBackground = true)
@Composable
fun ShowTestValue(viewModel: RegViewModel = viewModel()) {
    val donors by viewModel.donors.collectAsState()
    val requests by viewModel.requests.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            // Display Donors List
            BasicText("Donors:")
            donors.forEach { donor ->
                BasicText("- ${donor.name}, ${donor.blood_group}")
            }

            // Display Requests List
            BasicText("\nRequests:")
            requests.forEach { request ->
                BasicText("- ${request.user_name}, ${request.blood_group}")
            }
        }
    }
}
