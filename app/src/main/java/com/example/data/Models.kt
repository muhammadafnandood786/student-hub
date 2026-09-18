package com.example.data

enum class ResourceCategory(val displayName: String) {
    AGENT_DEVELOPMENT("Agent Development"),
    AI("AI & Generative Models"),
    GENERAL_AGENTS("General Agents"),
    CONTEXT_ENGINEERING("Context Engineering"),
    SPEC_DRIVEN_DEVELOPMENT("Spec-Driven Development"),
    MARKDOWN("Markdown & Documentation"),
    SOFTWARE_DEVELOPMENT("Software Development"),
    PROGRAMMING("TypeScript & Python"),
    CLOUD_DEPLOYMENT("Cloud / Deployment"),
    DEVELOPER_TOOLS("Developer Tools")
}

data class PanaversityResource(
    val id: String,
    val title: String,
    val description: String,
    val category: ResourceCategory,
    val course: String,
    val module: String,
    val chapter: String,
    val difficulty: String, // Beginner, Intermediate, Advanced
    val estimatedReadingTime: String,
    val officialSource: String, // e.g. "Panaversity GitBook / Official Docs"
    val officialUrl: String,
    val isVerified: Boolean = true,
    val isOfficial: Boolean = true,
    val isSaved: Boolean = false,
    val isCompleted: Boolean = false,
    val summary: String,
    val fullContent: String
)

data class Lesson(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val isCompleted: Boolean = false,
    val relatedResourceId: String? = null
)

data class Chapter(
    val id: String,
    val number: Int,
    val title: String,
    val description: String,
    val progress: Float, // 0.0 to 1.0
    val lessons: List<Lesson>,
    val relatedResourceId: String? = null,
    val quizId: String? = null
)

data class Course(
    val id: String,
    val title: String,
    val code: String,
    val description: String,
    val progress: Float,
    val chapters: List<Chapter>,
    val instructor: String
)

enum class AssignmentPriority { HIGH, MEDIUM, LOW }
enum class AssignmentStatus { PENDING, IN_PROGRESS, COMPLETED, OVERDUE }

data class Assignment(
    val id: String,
    val title: String,
    val course: String,
    val dueDate: String,
    val priority: AssignmentPriority,
    val progress: Float,
    val status: AssignmentStatus,
    val notes: String,
    val attachmentsCount: Int = 1
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class Quiz(
    val id: String,
    val title: String,
    val course: String,
    val chapter: String,
    val topic: String,
    val difficulty: String,
    val timerMinutes: Int,
    val questions: List<QuizQuestion>
)

data class QuizResultData(
    val quizTitle: String,
    val scorePercentage: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val timeSpent: String,
    val weakTopics: List<String>,
    val strongTopics: List<String>,
    val recommendedResourceId: String
)

data class SmartNote(
    val id: String,
    val title: String,
    val content: String,
    val tags: List<String>,
    val category: String,
    val isFavorite: Boolean = false,
    val updatedAt: String,
    val linkedResourceTitle: String? = null
)

data class CodingProblem(
    val id: String,
    val title: String,
    val difficulty: String,
    val category: String,
    val description: String,
    val starterCode: String,
    val expectedOutput: String
)

data class LearningPathNode(
    val id: String,
    val title: String,
    val status: String, // "COMPLETED", "IN_PROGRESS", "LOCKED"
    val progressPercent: Int,
    val description: String
)

data class CommunityPost(
    val id: String,
    val author: String,
    val authorRole: String,
    val timeAgo: String,
    val title: String,
    val content: String,
    val category: String, // Discussions, Questions, Study Groups, Project Showcase
    val likes: Int,
    val commentsCount: Int,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false,
    val tags: List<String>
)

data class NotificationItem(
    val id: String,
    val title: String,
    val description: String,
    val timeAgo: String,
    val type: String, // ASSIGNMENT, RESOURCE, QUIZ, STREAK, COMMUNITY
    val isRead: Boolean
)

data class TodayTask(
    val id: String,
    val title: String,
    val isCompleted: Boolean,
    val category: String
)

data class StudentProfile(
    val name: String,
    val rollNumber: String,
    val program: String,
    val batch: String,
    val skillLevel: String,
    val overallProgress: Int,
    val streakDays: Int,
    val coursesCount: Int,
    val chaptersCompleted: Int,
    val panaversityCompleted: Int,
    val assignmentsCompleted: Int,
    val quizAverage: Int
)

data class ChatMessage(
    val id: String,
    val sender: String, // "user" or "assistant"
    val text: String,
    val timestamp: String,
    val isCode: Boolean = false
)
