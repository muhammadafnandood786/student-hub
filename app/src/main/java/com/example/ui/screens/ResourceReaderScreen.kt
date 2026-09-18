package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PanaversityResource
import com.example.data.Repository
import com.example.ui.components.OfficialVerifiedBadge
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.VioletAi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceReaderScreen(
    resource: PanaversityResource,
    onBack: () -> Unit,
    onNavigateToQuiz: (String) -> Unit,
    onSaveToNotes: (String, String) -> Unit
) {
    val context = LocalContext.current
    var isBookmarked by remember { mutableStateOf(resource.isSaved) }
    var isCompleted by remember { mutableStateOf(resource.isCompleted) }
    var showAiToolSheet by remember { mutableStateOf(false) }
    var activeAiPrompt by remember { mutableStateOf<String?>(null) }
    var aiResultContent by remember { mutableStateOf<String?>(null) }
    var isAiGenerating by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    // Calculate reading progress based on scroll
    val progress = if (scrollState.maxValue > 0) {
        (scrollState.value.toFloat() / scrollState.maxValue.toFloat()).coerceIn(0f, 1f)
    } else 0f

    fun triggerAiAction(promptLabel: String) {
        activeAiPrompt = promptLabel
        isAiGenerating = true
        showAiToolSheet = true
        aiResultContent = null
    }

    LaunchedEffect(activeAiPrompt) {
        if (activeAiPrompt != null) {
            kotlinx.coroutines.delay(600)
            aiResultContent = Repository.generateAiResponse(activeAiPrompt!!, resource)
            isAiGenerating = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = resource.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Text(
                            text = "${resource.chapter} • ${resource.estimatedReadingTime}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { isBookmarked = !isBookmarked }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = { isCompleted = !isCompleted }) {
                        Icon(
                            imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.CheckCircleOutline,
                            contentDescription = "Mark Complete",
                            tint = if (isCompleted) EmeraldAccent else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(resource.officialUrl))
                        try { context.startActivity(intent) } catch (_: Exception) {}
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.OpenInNew,
                            contentDescription = "Open Official URL",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            // "Explain this" Signature AI Action Bar
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = VioletAi, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Explain with AI",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = VioletAi
                            )
                        }
                        Text(
                            text = "${(progress * 100).toInt()}% Read",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SuggestionChip(
                            onClick = { triggerAiAction("Explain simply like a beginner") },
                            label = { Text("Explain simply") },
                            icon = { Icon(Icons.Default.Lightbulb, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        )
                        SuggestionChip(
                            onClick = { triggerAiAction("Explain in Roman Urdu") },
                            label = { Text("Roman Urdu 🇵🇰") },
                            icon = { Icon(Icons.Default.Translate, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        )
                        SuggestionChip(
                            onClick = { triggerAiAction("Give a real world example") },
                            label = { Text("Real Example") },
                            icon = { Icon(Icons.Default.Code, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        )
                        SuggestionChip(
                            onClick = { triggerAiAction("Generate MCQs") },
                            label = { Text("Generate MCQs") },
                            icon = { Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        )
                        SuggestionChip(
                            onClick = { triggerAiAction("Generate flashcards") },
                            label = { Text("Flashcards") },
                            icon = { Icon(Icons.Default.Style, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        )
                        SuggestionChip(
                            onClick = { triggerAiAction("Summarize this lesson") },
                            label = { Text("Summarize") },
                            icon = { Icon(Icons.Default.ShortText, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Linear Progress Indicator for reading depth
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(20.dp)
            ) {
                // Breadcrumbs
                Text(
                    text = "${resource.course}  >  ${resource.module}  >  ${resource.chapter}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = resource.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OfficialVerifiedBadge(isOfficial = resource.isOfficial, isVerified = resource.isVerified)
                    Text(
                        text = "Source: ${resource.officialSource}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Summary Callout Box
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(14.dp)) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Key Takeaway",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = resource.summary,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Distraction-Free Reading Content
                Text(
                    text = resource.fullContent,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 26.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Bottom Navigation & Actions
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = CardDefaults.outlinedCardBorder(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Next Steps for this Chapter",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = { onNavigateToQuiz("q-agent-factory") },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Take Quiz")
                            }
                            Button(
                                onClick = {
                                    onSaveToNotes(resource.title, resource.summary)
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.NoteAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Save to Notes")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

    // AI Explanation Bottom Sheet Modal
    if (showAiToolSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAiToolSheet = false },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = VioletAi)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = activeAiPrompt ?: "AI Study Helper",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = { showAiToolSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (isAiGenerating) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = VioletAi)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Grounded AI is analyzing Panaversity curriculum...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else if (aiResultContent != null) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = aiResultContent!!,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(14.dp),
                            lineHeight = 22.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                onSaveToNotes(
                                    "AI: ${activeAiPrompt ?: "Note"}",
                                    aiResultContent ?: ""
                                )
                                showAiToolSheet = false
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Save as Note")
                        }

                        Button(
                            onClick = { showAiToolSheet = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Done")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
