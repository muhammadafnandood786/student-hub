package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.data.Assignment
import com.example.data.AssignmentPriority
import com.example.data.AssignmentStatus
import com.example.data.Repository
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.RoseError

@Composable
fun AssignmentManagerScreen(
    onNavigateToDetail: (Assignment) -> Unit
) {
    val assignments by Repository.assignments.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }
    var showCreateDialog by remember { mutableStateOf(false) }

    val filteredList = remember(assignments, selectedFilter) {
        when (selectedFilter) {
            "Pending" -> assignments.filter { it.status == AssignmentStatus.PENDING }
            "In Progress" -> assignments.filter { it.status == AssignmentStatus.IN_PROGRESS }
            "Completed" -> assignments.filter { it.status == AssignmentStatus.COMPLETED }
            "Overdue" -> assignments.filter { it.status == AssignmentStatus.OVERDUE }
            else -> assignments
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                modifier = Modifier.testTag("create_assignment_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Task")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("assignment_manager_screen")
        ) {
            // Header & Filter Chips
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Assignments & Tasks",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${assignments.count { it.status == AssignmentStatus.COMPLETED }} of ${assignments.size} submissions completed",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val filterOptions = listOf("All", "Pending", "In Progress", "Completed", "Overdue")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        filterOptions.forEach { filter ->
                            FilterChip(
                                selected = selectedFilter == filter,
                                onClick = { selectedFilter = filter },
                                label = { Text(filter) }
                            )
                        }
                    }
                }
            }

            // List of Assignments
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredList, key = { it.id }) { asg ->
                    AssignmentCard(
                        assignment = asg,
                        onToggleComplete = { Repository.toggleAssignmentStatus(asg.id) },
                        onClick = { onNavigateToDetail(asg) }
                    )
                }
            }
        }
    }

    // Create Task Modal Dialog
    if (showCreateDialog) {
        var newTitle by remember { mutableStateOf("") }
        var newNotes by remember { mutableStateOf("") }
        var newDueDate by remember { mutableStateOf("In 3 days") }
        var newPriority by remember { mutableStateOf(AssignmentPriority.HIGH) }

        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Create New Task / Assignment", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Task Title") },
                        placeholder = { Text("e.g. Build Zod schema validator") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newDueDate,
                        onValueChange = { newDueDate = it },
                        label = { Text("Due Date / Time") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newNotes,
                        onValueChange = { newNotes = it },
                        label = { Text("Notes / Panaversity References") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Priority:", style = MaterialTheme.typography.bodyMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            FilterChip(
                                selected = newPriority == AssignmentPriority.HIGH,
                                onClick = { newPriority = AssignmentPriority.HIGH },
                                label = { Text("High") }
                            )
                            FilterChip(
                                selected = newPriority == AssignmentPriority.MEDIUM,
                                onClick = { newPriority = AssignmentPriority.MEDIUM },
                                label = { Text("Med") }
                            )
                            FilterChip(
                                selected = newPriority == AssignmentPriority.LOW,
                                onClick = { newPriority = AssignmentPriority.LOW },
                                label = { Text("Low") }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTitle.isNotBlank()) {
                            Repository.addAssignment(
                                title = newTitle,
                                course = "Quarter 3: Agentic AI",
                                dueDate = newDueDate,
                                priority = newPriority,
                                notes = newNotes
                            )
                            showCreateDialog = false
                        }
                    }
                ) {
                    Text("Save Task")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun AssignmentCard(
    assignment: Assignment,
    onToggleComplete: () -> Unit,
    onClick: () -> Unit
) {
    val statusColor = when (assignment.status) {
        AssignmentStatus.COMPLETED -> EmeraldAccent
        AssignmentStatus.IN_PROGRESS -> MaterialTheme.colorScheme.primary
        AssignmentStatus.OVERDUE -> RoseError
        AssignmentStatus.PENDING -> AmberWarning
    }

    val priorityColor = when (assignment.priority) {
        AssignmentPriority.HIGH -> RoseError
        AssignmentPriority.MEDIUM -> AmberWarning
        AssignmentPriority.LOW -> EmeraldAccent
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("assignment_card_${assignment.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = priorityColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "${assignment.priority} PRIORITY",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = priorityColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = statusColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = assignment.status.name.replace("_", " "),
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = statusColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onToggleComplete,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (assignment.status == AssignmentStatus.COMPLETED) Icons.Default.CheckCircle else Icons.Default.CheckCircleOutline,
                        contentDescription = "Toggle Complete",
                        tint = if (assignment.status == AssignmentStatus.COMPLETED) EmeraldAccent else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = assignment.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = assignment.course,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Event, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Due: ${assignment.dueDate}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AttachFile, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${assignment.attachmentsCount} specs",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { assignment.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = statusColor,
                trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )
        }
    }
}
