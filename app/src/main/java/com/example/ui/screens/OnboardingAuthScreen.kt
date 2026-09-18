package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.GiaicGreen
import com.example.ui.theme.PanaversityBlue
import com.example.ui.theme.VioletAi

@Composable
fun OnboardingAuthScreen(
    onCompleteOnboarding: () -> Unit
) {
    var step by remember { mutableIntStateOf(1) }
    var selectedProgram by remember { mutableStateOf("Certified Agentic and Robotic AI Engineer") }
    var studentName by remember { mutableStateOf("Muhammad Afnan") }
    var rollNumber by remember { mutableStateOf("GIAIC-009418") }
    var autoSyncPanaversity by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("onboarding_auth_screen"),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Brand Logo Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(GiaicGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.School, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(PanaversityBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AutoStories, contentDescription = null, tint = Color.White, modifier = Modifier.size(26.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "GIAIC Student Hub",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "The Complete Student Operating System for GIAIC & Panaversity",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = if (step == 1) "Student Verification" else "Curriculum Sync",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    if (step == 1) {
                        OutlinedTextField(
                            value = studentName,
                            onValueChange = { studentName = it },
                            label = { Text("Full Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = rollNumber,
                            onValueChange = { rollNumber = it },
                            label = { Text("GIAIC Student Roll Number") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Enrolled Program",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        val programs = listOf(
                            "Certified Agentic and Robotic AI Engineer",
                            "Web3 and Metaverse Development",
                            "Cloud Native Microservices"
                        )

                        programs.forEach { prog ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedProgram == prog,
                                    onClick = { selectedProgram = prog }
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = prog,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (selectedProgram == prog) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { step = 2 },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Next: Connect Panaversity")
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    } else {
                        // Step 2: Panaversity curriculum connection
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldAccent)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Verified Student ID: $rollNumber",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.AutoStories, contentDescription = null, tint = PanaversityBlue, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Official Panaversity Repo Integration", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Direct sync with panaversity/learn-agentic-ai-hackathons and official reading specifications.",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Auto-sync reading progress", style = MaterialTheme.typography.bodySmall)
                            Switch(checked = autoSyncPanaversity, onCheckedChange = { autoSyncPanaversity = it })
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onCompleteOnboarding,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = GiaicGreen)
                        ) {
                            Text("Launch GIAIC Student Hub")
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.Default.RocketLaunch, contentDescription = null, modifier = Modifier.size(16.dp))
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        TextButton(
                            onClick = { step = 1 },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Back to Student Details")
                        }
                    }
                }
            }
        }
    }
}
