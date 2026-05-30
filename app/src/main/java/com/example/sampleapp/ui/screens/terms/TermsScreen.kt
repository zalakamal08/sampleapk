package com.example.sampleapp.ui.screens.terms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.ui.AppViewModel

@Composable
fun TermsScreen(
    viewModel: AppViewModel,
    onAccepted: () -> Unit,
    onDeclined: () -> Unit
) {
    val scrollState = rememberScrollState()
    val scrolledToBottom by remember {
        derivedStateOf { scrollState.maxValue > 0 && scrollState.value >= scrollState.maxValue - 4 }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .testTag("terms_screen")
    ) {
        Text(
            text = "Terms & Conditions",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Please read and scroll to the bottom to continue.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
                    .testTag("terms_content")
            ) {
                TermsBody.SECTIONS.forEachIndexed { index, section ->
                    Text(
                        text = "${index + 1}. ${section.first}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = if (index == 0) 0.dp else 16.dp, bottom = 6.dp)
                    )
                    Text(text = section.second, fontSize = 14.sp, lineHeight = 20.sp)
                }
            }
        }

        Text(
            text = if (scrolledToBottom) "You've reached the end — you can accept now."
            else "Scroll down to enable Accept.",
            fontSize = 12.sp,
            color = if (scrolledToBottom) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onDeclined,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .testTag("btn_decline")
            ) {
                Text("Decline")
            }
            Button(
                onClick = { viewModel.acceptTerms(onAccepted) },
                enabled = scrolledToBottom,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .testTag("btn_accept")
            ) {
                Text("Accept")
            }
        }
        Spacer(Modifier.height(4.dp))
    }
}

private object TermsBody {
    val SECTIONS = listOf(
        "Acceptance of Terms" to "By accessing or using SampleApk, you agree to be bound by these Terms and Conditions. This application is provided strictly as a sample for testing mobile UI automation, accessibility tooling, gesture automation, and agent-based interactions. It performs no real transactions and stores no data outside of your device.",
        "Local Data Storage" to "All information you enter — including account details, preferences, and generated activity — is stored locally on your device using an on-device database. No data is transmitted to any server, and the application functions fully offline. You are responsible for any data you choose to enter into this sample application.",
        "No Warranty" to "This software is provided \"as is\", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose, and non-infringement. The authors make no guarantee that the application will be error-free or uninterrupted.",
        "Acceptable Use" to "You agree not to use this application for any unlawful purpose. Because the app is intended for automation testing, you may freely script, scrape, and interact with its interface. Any dummy data shown is randomly generated and does not represent real people, accounts, or transactions.",
        "Privacy" to "SampleApk does not collect, transmit, or share any personal information. There are no analytics, advertising identifiers, or third-party trackers embedded in this build. Because everything is stored locally, uninstalling the app removes all associated data.",
        "Limitation of Liability" to "In no event shall the authors or copyright holders be liable for any claim, damages, or other liability, whether in an action of contract, tort, or otherwise, arising from, out of, or in connection with the software or the use or other dealings in the software.",
        "Changes to Terms" to "These terms may be updated in future versions of the sample application. Continued use after any change constitutes acceptance of the revised terms. As this is a testing artifact, no notification mechanism is implemented.",
        "Governing Provisions" to "These terms are provided for demonstration purposes only and do not constitute a legally binding agreement. By tapping Accept, you acknowledge that you understand this is sample content created for automation and quality-assurance testing.",
        "Contact" to "This is a sample application with no support channel. For questions about automation testing scenarios, refer to the project README included in the repository. Thank you for using SampleApk for your testing needs."
    )
}
