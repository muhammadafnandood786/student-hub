package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.screens.*
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.GiaicHubTheme
import com.example.ui.theme.VioletAi
import kotlinx.coroutines.launch

enum class NavigationTab(val title: String, val icon: ImageVector, val tag: String) {
    HOME("Home", Icons.Default.Home, "tab_home"),
    LEARN("Learn", Icons.AutoMirrored.Filled.MenuBook, "tab_learn"),
    PANAVERSITY("Panaversity", Icons.Default.Public, "tab_panaversity"),
    AI_STUDY("AI Study", Icons.Default.Psychology, "tab_ai"),
    TASKS("Tasks", Icons.Default.Checklist, "tab_tasks"),
    PROFILE("Profile", Icons.Default.Person, "tab_profile")
}

sealed class ActiveOverlay {
    data class ResourceReader(val resource: PanaversityResource) : ActiveOverlay()
    data class Quiz(val quizId: String) : ActiveOverlay()
    data object CodingLab : ActiveOverlay()
    data object SmartNotes : ActiveOverlay()
    data object Progress : ActiveOverlay()
    data object LearningPath : ActiveOverlay()
    data object Community : ActiveOverlay()
    data object Notifications : ActiveOverlay()
    data object Settings : ActiveOverlay()
    data object Onboarding : ActiveOverlay()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var themePreference by remember { mutableStateOf("System") } // System, Dark, Light
            val isDark = when (themePreference) {
                "Dark" -> true
                "Light" -> false
                else -> isSystemInDarkTheme()
            }

            GiaicHubTheme(darkTheme = isDark) {
                GiaicStudentHubApp(
                    themePreference = themePreference,
                    onThemeChange = { themePreference = it }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GiaicStudentHubApp(
    themePreference: String,
    onThemeChange: (String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var currentTab by remember { mutableStateOf(NavigationTab.HOME) }
    var activeOverlay by remember { mutableStateOf<ActiveOverlay?>(null) }
    var showGlobalSearch by remember { mutableStateOf(false) }
    var aiContextPrompt by remember { mutableStateOf<String?>(null) }

    val notifications by Repository.notifications.collectAsState()
    val unreadNotificationsCount = notifications.count { !it.isRead }

    // Intercept back button to dismiss active overlay or close drawer
    BackHandler(enabled = activeOverlay != null || drawerState.isOpen) {
        if (drawerState.isOpen) {
            coroutineScope.launch { drawerState.close() }
        } else if (activeOverlay != null) {
            activeOverlay = null
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = activeOverlay == null,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(310.dp),
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                // Drawer Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                        .padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.School,
                                    contentDescription = "GIAIC",
                                    tint = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "GIAIC Student Hub",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Panaversity Learning Portal",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Student: ${Repository.studentProfile.name} • ${Repository.studentProfile.rollNumber}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                HorizontalDivider()

                Spacer(modifier = Modifier.height(8.dp))

                // Core Sections
                Text(
                    text = "APPLICATION SECTIONS",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Dashboard") },
                    selected = activeOverlay == null && currentTab == NavigationTab.HOME,
                    onClick = {
                        activeOverlay = null
                        currentTab = NavigationTab.HOME
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                    label = { Text("Learn & Courses") },
                    selected = activeOverlay == null && currentTab == NavigationTab.LEARN,
                    onClick = {
                        activeOverlay = null
                        currentTab = NavigationTab.LEARN
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Public, contentDescription = null) },
                    label = { Text("Panaversity Hub") },
                    selected = activeOverlay == null && currentTab == NavigationTab.PANAVERSITY,
                    onClick = {
                        activeOverlay = null
                        currentTab = NavigationTab.PANAVERSITY
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Psychology, contentDescription = null) },
                    label = { Text("AI Study Assistant") },
                    selected = activeOverlay == null && currentTab == NavigationTab.AI_STUDY,
                    onClick = {
                        activeOverlay = null
                        currentTab = NavigationTab.AI_STUDY
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Checklist, contentDescription = null) },
                    label = { Text("Assignments & Tasks") },
                    selected = activeOverlay == null && currentTab == NavigationTab.TASKS,
                    onClick = {
                        activeOverlay = null
                        currentTab = NavigationTab.TASKS
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = "STUDENT TOOLS & FEATURES",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Code, contentDescription = null) },
                    label = { Text("Coding Lab") },
                    selected = activeOverlay is ActiveOverlay.CodingLab,
                    onClick = {
                        activeOverlay = ActiveOverlay.CodingLab
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.Notes, contentDescription = null) },
                    label = { Text("Smart Notes") },
                    selected = activeOverlay is ActiveOverlay.SmartNotes,
                    onClick = {
                        activeOverlay = ActiveOverlay.SmartNotes
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Quiz, contentDescription = null) },
                    label = { Text("Quizzes & Practice") },
                    selected = activeOverlay is ActiveOverlay.Quiz,
                    onClick = {
                        activeOverlay = ActiveOverlay.Quiz("quiz-1")
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Analytics, contentDescription = null) },
                    label = { Text("Progress & Analytics") },
                    selected = activeOverlay is ActiveOverlay.Progress,
                    onClick = {
                        activeOverlay = ActiveOverlay.Progress
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Map, contentDescription = null) },
                    label = { Text("Learning Roadmap") },
                    selected = activeOverlay is ActiveOverlay.LearningPath,
                    onClick = {
                        activeOverlay = ActiveOverlay.LearningPath
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Forum, contentDescription = null) },
                    label = { Text("Student Community") },
                    selected = activeOverlay is ActiveOverlay.Community,
                    onClick = {
                        activeOverlay = ActiveOverlay.Community
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Settings") },
                    selected = activeOverlay is ActiveOverlay.Settings,
                    onClick = {
                        activeOverlay = ActiveOverlay.Settings
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.RocketLaunch, contentDescription = null) },
                    label = { Text("Onboarding Flow") },
                    selected = activeOverlay is ActiveOverlay.Onboarding,
                    onClick = {
                        activeOverlay = ActiveOverlay.Onboarding
                        coroutineScope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                )
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                if (activeOverlay == null) {
                    TopAppBar(
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "GIAIC Hub",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 19.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = EmeraldAccent.copy(alpha = 0.15f),
                                    modifier = Modifier.padding(top = 1.dp)
                                ) {
                                    Text(
                                        text = "PANAVERSITY",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = EmeraldAccent,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { coroutineScope.launch { drawerState.open() } },
                                modifier = Modifier.testTag("open_nav_drawer")
                            ) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            // Streak Pill
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.padding(end = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.LocalFireDepartment,
                                        contentDescription = "Streak",
                                        tint = Color(0xFFF97316),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "14d",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFFF97316)
                                    )
                                }
                            }

                            // Global Search Icon
                            IconButton(
                                onClick = { showGlobalSearch = true },
                                modifier = Modifier.testTag("action_global_search")
                            ) {
                                Icon(Icons.Default.Search, contentDescription = "Global Search")
                            }

                            // Notifications Icon with badge
                            IconButton(
                                onClick = { activeOverlay = ActiveOverlay.Notifications },
                                modifier = Modifier.testTag("action_notifications")
                            ) {
                                BadgedBox(
                                    badge = {
                                        if (unreadNotificationsCount > 0) {
                                            Badge { Text("$unreadNotificationsCount") }
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            },
            bottomBar = {
                if (activeOverlay == null) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp,
                        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                    ) {
                        NavigationTab.entries.forEach { tab ->
                            NavigationBarItem(
                                selected = currentTab == tab,
                                onClick = { currentTab = tab },
                                icon = {
                                    Icon(
                                        tab.icon,
                                        contentDescription = tab.title,
                                        modifier = Modifier.size(22.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = tab.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 10.sp
                                    )
                                },
                                modifier = Modifier.testTag(tab.tag)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Main Views switching with AnimatedContent
                if (activeOverlay != null) {
                    when (val overlay = activeOverlay) {
                        is ActiveOverlay.ResourceReader -> {
                            ResourceReaderScreen(
                                resource = overlay.resource,
                                onBack = { activeOverlay = null },
                                onNavigateToQuiz = { quizId -> activeOverlay = ActiveOverlay.Quiz(quizId) },
                                onSaveToNotes = { title, content ->
                                    Repository.addNote(
                                        title = title,
                                        content = content,
                                        category = "Resource Notes",
                                        tags = listOf("Panaversity", "Spec"),
                                        linkedResourceTitle = overlay.resource.title
                                    )
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Note saved to Smart Notes!")
                                    }
                                }
                            )
                        }
                        is ActiveOverlay.Quiz -> {
                            QuizScreen(
                                quizId = overlay.quizId,
                                onBack = { activeOverlay = null },
                                onAskAi = { prompt ->
                                    activeOverlay = null
                                    currentTab = NavigationTab.AI_STUDY
                                    aiContextPrompt = prompt
                                },
                                onOpenResource = { resId ->
                                    val res = Repository.officialResources.firstOrNull { it.id == resId }
                                        ?: Repository.officialResources.first()
                                    activeOverlay = ActiveOverlay.ResourceReader(res)
                                }
                            )
                        }
                        is ActiveOverlay.CodingLab -> {
                            CodingLabScreen(
                                onNavigateToAiHelp = { codeSnippet ->
                                    activeOverlay = null
                                    currentTab = NavigationTab.AI_STUDY
                                    aiContextPrompt = "Can you help optimize or explain this code:\n$codeSnippet"
                                }
                            )
                        }
                        is ActiveOverlay.SmartNotes -> {
                            SmartNotesScreen(
                                onOpenAiWithNote = { noteContent ->
                                    activeOverlay = null
                                    currentTab = NavigationTab.AI_STUDY
                                    aiContextPrompt = "Please summarize and improve this note:\n$noteContent"
                                }
                            )
                        }
                        is ActiveOverlay.Progress -> {
                            ProgressScreen(
                                onNavigateToLearningPath = { activeOverlay = ActiveOverlay.LearningPath },
                                onNavigateToResource = { resId ->
                                    val res = Repository.officialResources.firstOrNull { it.id == resId }
                                        ?: Repository.officialResources.first()
                                    activeOverlay = ActiveOverlay.ResourceReader(res)
                                }
                            )
                        }
                        is ActiveOverlay.LearningPath -> {
                            LearningPathScreen(
                                onBack = { activeOverlay = null },
                                onNavigateToActiveCourse = {
                                    activeOverlay = null
                                    currentTab = NavigationTab.LEARN
                                }
                            )
                        }
                        is ActiveOverlay.Community -> {
                            CommunityScreen()
                        }
                        is ActiveOverlay.Notifications -> {
                            NotificationsScreen(
                                onBack = { activeOverlay = null }
                            )
                        }
                        is ActiveOverlay.Settings -> {
                            SettingsScreen(
                                onBack = { activeOverlay = null },
                                currentTheme = themePreference,
                                onThemeChange = onThemeChange
                            )
                        }
                        is ActiveOverlay.Onboarding -> {
                            OnboardingAuthScreen(
                                onCompleteOnboarding = { activeOverlay = null }
                            )
                        }
                        null -> {}
                    }
                } else {
                    when (currentTab) {
                        NavigationTab.HOME -> {
                            DashboardScreen(
                                onNavigateToPanaversity = { currentTab = NavigationTab.PANAVERSITY },
                                onNavigateToLearn = { currentTab = NavigationTab.LEARN },
                                onNavigateToAiAssistant = { prompt ->
                                    aiContextPrompt = prompt
                                    currentTab = NavigationTab.AI_STUDY
                                },
                                onNavigateToCodingLab = { activeOverlay = ActiveOverlay.CodingLab },
                                onNavigateToAssignments = { currentTab = NavigationTab.TASKS },
                                onNavigateToQuizzes = { activeOverlay = ActiveOverlay.Quiz("quiz-1") },
                                onNavigateToLearningPath = { activeOverlay = ActiveOverlay.LearningPath }
                            )
                        }
                        NavigationTab.LEARN -> {
                            LearnScreen(
                                onSelectResource = { res -> activeOverlay = ActiveOverlay.ResourceReader(res) },
                                onNavigateToQuiz = { quizId -> activeOverlay = ActiveOverlay.Quiz(quizId) },
                                onNavigateToAiAssistant = { prompt ->
                                    aiContextPrompt = prompt
                                    currentTab = NavigationTab.AI_STUDY
                                },
                                onNavigateToNotes = { activeOverlay = ActiveOverlay.SmartNotes }
                            )
                        }
                        NavigationTab.PANAVERSITY -> {
                            PanaversityHubScreen(
                                onSelectResource = { res -> activeOverlay = ActiveOverlay.ResourceReader(res) },
                                onNavigateToAiAssistantWithContext = { prompt ->
                                    aiContextPrompt = prompt
                                    currentTab = NavigationTab.AI_STUDY
                                }
                            )
                        }
                        NavigationTab.AI_STUDY -> {
                            AiStudyAssistantScreen(
                                initialPrompt = aiContextPrompt,
                                onSaveToNotes = { title, notesContent ->
                                    Repository.addNote(
                                        title = title,
                                        content = notesContent,
                                        category = "AI Generated",
                                        tags = listOf("AI Assistant", "GIAIC", "Study")
                                    )
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Saved to Smart Notes!")
                                    }
                                }
                            )
                        }
                        NavigationTab.TASKS -> {
                            AssignmentManagerScreen(
                                onNavigateToDetail = { assignment ->
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("${assignment.title}: ${assignment.notes.take(60)}...")
                                    }
                                }
                            )
                        }
                        NavigationTab.PROFILE -> {
                            ProfileScreen(
                                onNavigateToSettings = { activeOverlay = ActiveOverlay.Settings }
                            )
                        }
                    }
                }

                // Global Search Modal
                if (showGlobalSearch) {
                    GlobalSearchDialog(
                        onDismiss = { showGlobalSearch = false },
                        onSelectResource = { res ->
                            showGlobalSearch = false
                            activeOverlay = ActiveOverlay.ResourceReader(res)
                        },
                        onNavigateToQuiz = { quizId ->
                            showGlobalSearch = false
                            activeOverlay = ActiveOverlay.Quiz(quizId)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

