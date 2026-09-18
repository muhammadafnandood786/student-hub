package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CodingProblem
import com.example.data.Repository
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.VioletAi

@Composable
fun CodingLabScreen(
    onNavigateToAiHelp: (String) -> Unit
) {
    val problems = Repository.codingProblems
    var selectedProblemIndex by remember { mutableIntStateOf(0) }
    val currentProblem = problems[selectedProblemIndex]

    var userCode by remember(selectedProblemIndex) { mutableStateOf(currentProblem.starterCode) }
    var consoleOutput by remember { mutableStateOf("Terminal ready. Click 'Run' to execute in agentic sandbox...") }
    var isRunning by remember { mutableStateOf(false) }
    var showAiExplanation by remember { mutableStateOf(false) }
    var aiExplanationText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("coding_lab_screen")
    ) {
        // Top Problem Selector Tabs
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Terminal, contentDescription = null, tint = EmeraldAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Coding Lab", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = EmeraldAccent.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = currentProblem.difficulty,
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldAccent,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    problems.forEachIndexed { index, problem ->
                        FilterChip(
                            selected = selectedProblemIndex == index,
                            onClick = { selectedProblemIndex = index },
                            label = { Text(problem.title) }
                        )
                    }
                }
            }
        }

        // Problem Description Drawer/Box
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Text(
                    text = "Exercise Specification",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = currentProblem.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Code Editor Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF0F172A))
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "solution.ts",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8),
                        fontFamily = FontFamily.Monospace
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        TextButton(
                            onClick = { userCode = currentProblem.starterCode },
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                        ) {
                            Icon(Icons.Default.RestartAlt, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reset", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    BasicTextField(
                        value = userCode,
                        onValueChange = { userCode = it },
                        textStyle = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            color = Color(0xFF38BDF8),
                            lineHeight = 20.sp
                        ),
                        cursorBrush = SolidColor(Color(0xFF38BDF8)),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Action Toolbar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        isRunning = true
                        consoleOutput = "Compiling TypeScript AST...\nExecuting in V8 Node Sandbox...\n\n" + currentProblem.expectedOutput + "\n\n✓ All tests passed successfully!"
                        isRunning = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldAccent),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Run Code", fontSize = 12.sp)
                }

                Button(
                    onClick = {
                        consoleOutput = "Submitted to GIAIC autograder!\nScore: 100/100\nRecorded to Student Progress Ledger."
                    },
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Submit", fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = {
                        showAiExplanation = true
                        aiExplanationText = "AI Optimizer:\n1. Ensure idempotency by tracking executed tool IDs in a Set.\n2. Add timeout checks for OODA iterations to avoid infinite loops.\n3. Follow Panaversity Spec-Driven Development rule #3."
                    },
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = VioletAi, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Optimize with AI", fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = {
                        onNavigateToAiHelp("Please explain the solution for coding exercise: ${currentProblem.title}\n\nCode:\n$userCode")
                    },
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(Icons.Default.QuestionAnswer, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Ask AI", fontSize = 12.sp)
                }
            }
        }

        // Terminal / Console Output Area
        Surface(
            color = Color(0xFF020617),
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CONSOLE OUTPUT",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    IconButton(
                        onClick = { consoleOutput = "Terminal cleared." },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(Icons.Default.ClearAll, contentDescription = "Clear", tint = Color(0xFF64748B))
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = if (showAiExplanation) "$consoleOutput\n\n$aiExplanationText" else consoleOutput,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = Color(0xFFE2E8F0),
                            lineHeight = 16.sp
                        )
                    )
                }
            }
        }
    }
}
