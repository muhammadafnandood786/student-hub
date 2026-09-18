package com.example.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Repository {

    val studentProfile = StudentProfile(
        name = "Muhammad Afnan",
        rollNumber = "GIAIC-009418",
        program = "Certified Agentic and Robotic AI Engineer",
        batch = "Batch 1 (Q3)",
        skillLevel = "Intermediate Explorer",
        overallProgress = 72,
        streakDays = 14,
        coursesCount = 3,
        chaptersCompleted = 18,
        panaversityCompleted = 24,
        assignmentsCompleted = 9,
        quizAverage = 88
    )

    val officialResources = listOf(
        PanaversityResource(
            id = "res-1",
            title = "Agent Factory Paradigm & Autonomous Architecture",
            description = "Fundamental architecture of multi-agent loops, state machines, and autonomous agent coordination according to Panaversity standard specifications.",
            category = ResourceCategory.AGENT_DEVELOPMENT,
            course = "Quarter 3: Agentic AI",
            module = "Module 1: Agent Architectures",
            chapter = "Chapter 1: Agent Factory Paradigm",
            difficulty = "Intermediate",
            estimatedReadingTime = "12 min read",
            officialSource = "Official Panaversity Open Guide",
            officialUrl = "https://panaversity.org/agents/agent-factory",
            isVerified = true,
            isOfficial = true,
            isSaved = true,
            isCompleted = true,
            summary = "Explores the Agent Factory design pattern where agents are created deterministically via schemas, dynamic tool-bindings, and memory anchors.",
            fullContent = """
# Agent Factory Paradigm

The Agent Factory is a centralized pattern for creating, configuring, and orchestrating autonomous AI agents with predictable behaviors, secure tool permissions, and contextual boundaries.

## 1. Core Principles
- **Specification-First**: Agents are initialized through declarative specifications rather than ad-hoc code prompts.
- **Dynamic Tool Injection**: Each agent receives only the tools required for its discrete functional domain.
- **State Serialization**: Agent memories and execution traces are continuously checkpointed.

```typescript
interface AgentConfig {
  name: string;
  systemPrompt: string;
  tools: ToolDefinition[];
  memoryPolicy: "ephemeral" | "persisted_sqlite";
}

class AgentFactory {
  static create(spec: AgentConfig): AutonomousAgent {
    return new AutonomousAgent(spec);
  }
}
```

## 2. Loop Execution Model
Autonomous reasoning loops follow the **Observe -> Orient -> Decide -> Act (OODA)** cycle:
1. **Perception**: Receiving structured input from environment or user.
2. **Context Assembly**: Retrieving relevant semantic chunks.
3. **Execution**: Calling tools or generating output.
4. **Reflection**: Evaluating whether termination criteria are met.
            """.trimIndent()
        ),
        PanaversityResource(
            id = "res-2",
            title = "Context Engineering: Token Budgets & Memory Anchoring",
            description = "Official guide on structuring system prompts, sliding windows, and hierarchical retrieval to maintain fidelity without token exhaustion.",
            category = ResourceCategory.CONTEXT_ENGINEERING,
            course = "Quarter 3: Agentic AI",
            module = "Module 2: Memory & Context",
            chapter = "Chapter 4: Context Engineering",
            difficulty = "Advanced",
            estimatedReadingTime = "15 min read",
            officialSource = "Official Panaversity Engineering Handbook",
            officialUrl = "https://panaversity.org/curriculum/context-engineering",
            isVerified = true,
            isOfficial = true,
            isSaved = false,
            isCompleted = false,
            summary = "Covers token pruning strategies, dynamic KV caching considerations, and multi-tier memory recall.",
            fullContent = """
# Context Engineering in Modern LLM Systems

Context Engineering is the discipline of maximizing useful intelligence per prompt token while preventing context drift and hallucinations.

## Key Tenets:
1. **Dynamic Budgeting**: Reserving 30% of max tokens for generative reasoning and tool schemas.
2. **Anchor Placement**: Placing crucial invariant instructions at the start and end of prompt payloads (the Serial-Position Effect in LLMs).
3. **Semantic Distillation**: Summarizing previous turns rather than appending raw conversation history.

```markdown
<!-- Structure of an Engineered Context -->
<system_identity>Role, safety constraints, tool guidelines</system_identity>
<domain_knowledge>Deterministic ground-truth data</domain_knowledge>
<episodic_history>Distilled prior turns</episodic_history>
<user_goal>Direct imperative task</user_goal>
```
            """.trimIndent()
        ),
        PanaversityResource(
            id = "res-3",
            title = "General Agents vs Special Purpose Task Workers",
            description = "Comparing generalized reasoning loops with highly specialized deterministic micro-agents in production environments.",
            category = ResourceCategory.GENERAL_AGENTS,
            course = "Quarter 3: Agentic AI",
            module = "Module 1: Agent Architectures",
            chapter = "Chapter 3: General Agents",
            difficulty = "Intermediate",
            estimatedReadingTime = "10 min read",
            officialSource = "Panaversity Knowledge Base",
            officialUrl = "https://panaversity.org/curriculum/general-agents",
            isVerified = true,
            isOfficial = true,
            isSaved = true,
            isCompleted = false,
            summary = "Deconstructs single monolithic agents into coordinated swarms with a Supervisor agent delegating to specialists.",
            fullContent = """
# General Agents: Swarm Architecture

While a General Agent can answer varied queries, production AI requires **Delegation Topologies**.

### Topology Hierarchy:
- **Supervisor (Orchestrator)**: Parses user intents and produces an execution plan.
- **Domain Worker (Code, Search, DB)**: Executes tools within sandboxed runtime.
- **Critic / Verifier**: Evaluates worker output before delivering to user.
            """.trimIndent()
        ),
        PanaversityResource(
            id = "res-4",
            title = "Spec-Driven Development (SDD) for Generative Software",
            description = "Standard operational procedure for writing rigorous schemas, contracts, and unit assertions before prompt generation.",
            category = ResourceCategory.SPEC_DRIVEN_DEVELOPMENT,
            course = "Quarter 3: Agentic AI",
            module = "Module 3: Methodology",
            chapter = "Chapter 5: Spec-Driven Development",
            difficulty = "Beginner",
            estimatedReadingTime = "8 min read",
            officialSource = "Panaversity Best Practices",
            officialUrl = "https://panaversity.org/spec-driven-dev",
            isVerified = true,
            isOfficial = true,
            isSaved = false,
            isCompleted = true,
            summary = "Ensuring AI agents adhere to typed JSON contracts rather than unstructured prose.",
            fullContent = """
# Spec-Driven Development (SDD)

Never ask an LLM to generate code without a schema. By anchoring generation against OpenAPI, TypeScript interfaces, or Protobuf specs, output predictability increases by 90%.
            """.trimIndent()
        ),
        PanaversityResource(
            id = "res-5",
            title = "Markdown Writing Instructions & Semantic Tagging",
            description = "How to write structured technical markdown documentation that AI parsers and human readers can parse seamlessly.",
            category = ResourceCategory.MARKDOWN,
            course = "Quarter 3: Agentic AI",
            module = "Module 1: Foundations",
            chapter = "Chapter 2: Markdown Writing Instructions",
            difficulty = "Beginner",
            estimatedReadingTime = "6 min read",
            officialSource = "Panaversity Documentation Portal",
            officialUrl = "https://panaversity.org/docs/markdown-rules",
            isVerified = true,
            isOfficial = true,
            isSaved = false,
            isCompleted = true,
            summary = "Tagging rules, code fences, frontmatter metadata, and heading discipline.",
            fullContent = """
# Markdown Standard for AI Systems

Consistent markdown headings (`#`, `##`), typed codeblocks (````typescript`), and bullet hierarchies allow agents to index documents into vector embeddings cleanly.
            """.trimIndent()
        ),
        PanaversityResource(
            id = "res-6",
            title = "The Seven Principles of Agentic Software Engineering",
            description = "Foundational tenets governing safety, determinism, failover strategies, and feedback loops in autonomous software.",
            category = ResourceCategory.SOFTWARE_DEVELOPMENT,
            course = "Quarter 3: Agentic AI",
            module = "Module 3: Methodology",
            chapter = "Chapter 6: Seven Principles",
            difficulty = "Intermediate",
            estimatedReadingTime = "14 min read",
            officialSource = "Panaversity AI Engineering Whitepaper",
            officialUrl = "https://panaversity.org/principles/seven-principles",
            isVerified = true,
            isOfficial = true,
            isSaved = true,
            isCompleted = false,
            summary = "Safety, sandboxing, determinism, observability, human-in-the-loop, progressive recovery, and idempotency.",
            fullContent = """
# Seven Principles of Agentic Systems
1. **Idempotence**: Re-running an action must not cause duplicate side effects.
2. **Least Privilege**: Only provide tools with safe boundaries.
3. **Observability**: Every token and tool invocation must be logged.
4. **Graceful Degradation**: Fallback to heuristic defaults when LLM fails.
5. **Human-in-the-Loop**: High-consequence operations require human confirmation.
6. **Grounding**: Always cite authoritative resources.
7. **Modularity**: Small decoupled agents beat monolithic giants.
            """.trimIndent()
        ),
        PanaversityResource(
            id = "res-7",
            title = "Community Guide to Docker & Cloud Deployment for AI Agents",
            description = "Guide curated by community contributors on packaging FastAPI & LangGraph agents inside lightweight Docker containers.",
            category = ResourceCategory.CLOUD_DEPLOYMENT,
            course = "Quarter 3: Agentic AI",
            module = "Module 4: Deployment",
            chapter = "Chapter 7: Containerization",
            difficulty = "Intermediate",
            estimatedReadingTime = "11 min read",
            officialSource = "External Student Community Resource",
            officialUrl = "https://github.com/panaversity-students/agent-docker-guide",
            isVerified = false,
            isOfficial = false,
            isSaved = false,
            isCompleted = false,
            summary = "Community written Dockerfile templates for running Python AI agents with multi-stage builds.",
            fullContent = """
# Community Deployment Guide
*Note: This is an External / Unverified Resource contributed by student community members.*

Step-by-step instructions for running agents on Cloud Run and VPS instances using lightweight alpine containers.
            """.trimIndent()
        )
    )

    val giaicCourses = listOf(
        Course(
            id = "c-q3",
            title = "Quarter 3: Certified Agentic and Robotic AI",
            code = "AI-301",
            description = "Mastering Autonomous Agents, Prompt Engineering, LangGraph, and Panaversity Specification Patterns.",
            progress = 0.72f,
            instructor = "Sir Zia Khan & Panaversity Faculty",
            chapters = listOf(
                Chapter(
                    id = "ch-1",
                    number = 1,
                    title = "Agent Factory Paradigm",
                    description = "Constructing modular, reproducible autonomous agents using factory abstractions and state tracking.",
                    progress = 1.0f,
                    lessons = listOf(
                        Lesson("l-101", "Introduction to Agentic Architectures", 20, true, "res-1"),
                        Lesson("l-102", "Factory Design Pattern in TypeScript", 35, true, "res-1"),
                        Lesson("l-103", "Dynamic Tool Registration", 25, true, "res-1")
                    ),
                    relatedResourceId = "res-1",
                    quizId = "q-agent-factory"
                ),
                Chapter(
                    id = "ch-2",
                    number = 2,
                    title = "Markdown Writing Instructions",
                    description = "Semantic documentation, LLM ingestion best practices, and structured prompting syntax.",
                    progress = 1.0f,
                    lessons = listOf(
                        Lesson("l-201", "Semantic Tagging and Markdown AST", 15, true, "res-5"),
                        Lesson("l-202", "Writing Clean Specifications for LLMs", 25, true, "res-5")
                    ),
                    relatedResourceId = "res-5",
                    quizId = "q-markdown"
                ),
                Chapter(
                    id = "ch-3",
                    number = 3,
                    title = "General Agents",
                    description = "Multi-step reasoning, tool coordination, and orchestrator-worker swarms.",
                    progress = 0.65f,
                    lessons = listOf(
                        Lesson("l-301", "Reasoning Loops & Thought Tracing", 30, true, "res-3"),
                        Lesson("l-302", "Supervisor Delegation Workflows", 40, false, "res-3"),
                        Lesson("l-303", "Handling Tool Execution Errors", 20, false, "res-3")
                    ),
                    relatedResourceId = "res-3",
                    quizId = "q-general-agents"
                ),
                Chapter(
                    id = "ch-4",
                    number = 4,
                    title = "Context Engineering",
                    description = "Maximizing prompt signal-to-noise ratio, memory anchoring, and token budget strategies.",
                    progress = 0.40f,
                    lessons = listOf(
                        Lesson("l-401", "Understanding LLM Attention & Lost-in-the-Middle", 25, true, "res-2"),
                        Lesson("l-402", "Sliding Window & Vector Memory Recall", 35, false, "res-2"),
                        Lesson("l-403", "Context Compression & Distillation", 30, false, "res-2")
                    ),
                    relatedResourceId = "res-2",
                    quizId = "q-context-eng"
                ),
                Chapter(
                    id = "ch-5",
                    number = 5,
                    title = "Spec-Driven Development (SDD)",
                    description = "Designing schemas, interface contracts, and automated validation gates.",
                    progress = 0.85f,
                    lessons = listOf(
                        Lesson("l-501", "Type-safe JSON Generation with Zod", 25, true, "res-4"),
                        Lesson("l-502", "Automated Contract Verification", 30, true, "res-4")
                    ),
                    relatedResourceId = "res-4",
                    quizId = "q-sdd"
                ),
                Chapter(
                    id = "ch-6",
                    number = 6,
                    title = "Seven Principles of Agentic Engineering",
                    description = "Core philosophical and architectural pillars for production-grade AI reliability.",
                    progress = 0.20f,
                    lessons = listOf(
                        Lesson("l-601", "Idempotence & Safe Tool Boundaries", 20, true, "res-6"),
                        Lesson("l-602", "Human-in-the-loop Protocol", 30, false, "res-6"),
                        Lesson("l-603", "Observability & Tracing Best Practices", 30, false, "res-6")
                    ),
                    relatedResourceId = "res-6",
                    quizId = "q-principles"
                )
            )
        ),
        Course(
            id = "c-q2",
            title = "Quarter 2: Next.js & Modern Full-Stack",
            code = "WEB-201",
            description = "Server Components, App Router, Tailwind CSS, and Cloud Database integrations.",
            progress = 1.0f,
            instructor = "Sir Zia Khan & Team",
            chapters = emptyList()
        ),
        Course(
            id = "c-q1",
            title = "Quarter 1: TypeScript & Programming Fundamentals",
            code = "TS-101",
            description = "Core algorithms, strongly typed programming, Node.js CLI projects.",
            progress = 1.0f,
            instructor = "Sir Zia Khan",
            chapters = emptyList()
        )
    )

    private val _assignments = MutableStateFlow(
        listOf(
            Assignment(
                id = "asg-1",
                title = "Build an Autonomous Agent Factory with Tool Binding",
                course = "Quarter 3: Agentic AI",
                dueDate = "Tomorrow, 11:59 PM",
                priority = AssignmentPriority.HIGH,
                progress = 0.75f,
                status = AssignmentStatus.IN_PROGRESS,
                notes = "Remember to follow the official Panaversity Agent Factory specification and test idempotency on tool retries.",
                attachmentsCount = 2
            ),
            Assignment(
                id = "asg-2",
                title = "Context Window Budgeting & Compression Matrix",
                course = "Quarter 3: Agentic AI",
                dueDate = "Sep 22, 2026",
                priority = AssignmentPriority.MEDIUM,
                progress = 0.20f,
                status = AssignmentStatus.PENDING,
                notes = "Calculate token overhead for a 5-step conversation using Gemini 1.5 Flash and implement dynamic summarization.",
                attachmentsCount = 1
            ),
            Assignment(
                id = "asg-3",
                title = "Spec-Driven Development Contract Schema in Zod",
                course = "Quarter 3: Agentic AI",
                dueDate = "Sep 15, 2026",
                priority = AssignmentPriority.MEDIUM,
                progress = 1.0f,
                status = AssignmentStatus.COMPLETED,
                notes = "Graded: 98/100. Excellent usage of union discriminators and strict schema validation.",
                attachmentsCount = 3
            ),
            Assignment(
                id = "asg-4",
                title = "Markdown Technical Specification for Swarm Coordinator",
                course = "Quarter 3: Agentic AI",
                dueDate = "Sep 10, 2026",
                priority = AssignmentPriority.LOW,
                progress = 1.0f,
                status = AssignmentStatus.COMPLETED,
                notes = "Submission verified by teaching assistant.",
                attachmentsCount = 1
            ),
            Assignment(
                id = "asg-5",
                title = "Deploy LangGraph Agent to Cloud Run Container",
                course = "Quarter 3: Agentic AI",
                dueDate = "Sep 01, 2026",
                priority = AssignmentPriority.HIGH,
                progress = 0.0f,
                status = AssignmentStatus.OVERDUE,
                notes = "Needs immediate submission to unlock certification milestone.",
                attachmentsCount = 1
            )
        )
    )
    val assignments: StateFlow<List<Assignment>> = _assignments.asStateFlow()

    private val _notes = MutableStateFlow(
        listOf(
            SmartNote(
                id = "note-1",
                title = "Agent Factory vs Singleton Agent Pattern",
                content = "Factories allow instantiating multiple isolated worker agents with customized runtime tools. Singletons suffer from context bleed and shared state pollution during parallel tasks.\n\nKey advantages:\n- Clean memory boundary per request\n- Deterministic teardown and garbage collection\n- Configurable LLM temperature per task",
                tags = listOf("Agentic", "Architecture", "Patterns"),
                category = "Panaversity Notes",
                isFavorite = true,
                updatedAt = "2 hours ago",
                linkedResourceTitle = "Agent Factory Paradigm & Autonomous Architecture"
            ),
            SmartNote(
                id = "note-2",
                title = "Context Engineering: The 30% Token Rule",
                content = "Always keep 30% buffer for generation tokens and JSON tool calls.\nSystem prompt must place invariant constraints in the first 200 tokens. Variable context in the middle. Recent turns right before completion trigger.",
                tags = listOf("PromptEng", "Memory", "Tokens"),
                category = "Study Guides",
                isFavorite = true,
                updatedAt = "Yesterday",
                linkedResourceTitle = "Context Engineering: Token Budgets & Memory Anchoring"
            ),
            SmartNote(
                id = "note-3",
                title = "Roman Urdu Quick Revision: OODA Loop",
                content = "OODA Loop ka aasan matlab:\n1. Observe: Mahol aur sawal ko dekhna\n2. Orient: Puranay knowledge aur rules ko match karna\n3. Decide: Agla qadam chun'na (kaunsa tool chalana hai)\n4. Act: Tool execute karna aur result user ko dena.",
                tags = listOf("RomanUrdu", "AI", "Basics"),
                category = "AI Explanations",
                isFavorite = false,
                updatedAt = "Sep 15, 2026",
                linkedResourceTitle = "General Agents vs Special Purpose Task Workers"
            )
        )
    )
    val notes: StateFlow<List<SmartNote>> = _notes.asStateFlow()

    val sampleQuizzes = listOf(
        Quiz(
            id = "q-agent-factory",
            title = "Agent Factory & Autonomous Paradigms",
            course = "Quarter 3: Agentic AI",
            chapter = "Chapter 1: Agent Factory Paradigm",
            topic = "Factory Architecture & Lifecycles",
            difficulty = "Intermediate",
            timerMinutes = 15,
            questions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "In the Panaversity Agent Factory paradigm, why is deterministic specification preferred over ad-hoc prompting?",
                    options = listOf(
                        "It prevents context drift and ensures predictable tool execution across agent instances",
                        "It reduces GPU memory usage to zero",
                        "It allows agents to execute without any language model",
                        "It converts Python scripts into binary assembly"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Deterministic specifications define strict input/output schemas and bounded tools, preventing hallucination and context drift."
                ),
                QuizQuestion(
                    id = 2,
                    question = "Which phase of the OODA loop is responsible for synthesizing perceptual inputs with prior memory anchors?",
                    options = listOf(
                        "Act",
                        "Orient",
                        "Observe",
                        "Decide"
                    ),
                    correctOptionIndex = 1,
                    explanation = "Orient is where the agent synthesizes perceptual input (from Observe) against memory anchors, schemas, and prior knowledge."
                ),
                QuizQuestion(
                    id = 3,
                    question = "What is the primary risk of sharing stateful singleton agents across concurrent user sessions?",
                    options = listOf(
                        "Cross-tenant context bleed and unpredictable memory contamination",
                        "Excessive battery consumption",
                        "HTTP 404 file not found errors",
                        "Slow compile times"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Singleton agents retain conversation memories in memory, which risks leaking private data across concurrent user turns."
                ),
                QuizQuestion(
                    id = 4,
                    question = "According to Panaversity Spec-Driven Development, what artifact should be designed first?",
                    options = listOf(
                        "The final user interface layout",
                        "The typed interface or schema contract (e.g., Zod / JSON Schema)",
                        "A random generative prompt",
                        "The marketing pitch deck"
                    ),
                    correctOptionIndex = 1,
                    explanation = "Spec-Driven Development dictates defining strict schema contracts first so agents generate validated structured outputs."
                )
            )
        ),
        Quiz(
            id = "q-context-eng",
            title = "Context Engineering & Token Management",
            course = "Quarter 3: Agentic AI",
            chapter = "Chapter 4: Context Engineering",
            topic = "Token Optimization & Memory Anchoring",
            difficulty = "Advanced",
            timerMinutes = 12,
            questions = listOf(
                QuizQuestion(
                    id = 1,
                    question = "What is the 'Lost-in-the-Middle' phenomenon in large context windows?",
                    options = listOf(
                        "Models recall information best at the start and end of prompt context, but recall degrades in the middle",
                        "The internet connection drops halfway through a request",
                        "The model runs out of RAM during vector quantization",
                        "Tokens are randomly deleted by the tokenizer"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Empirical research shows transformers pay higher attention weights to beginning and ending tokens, potentially ignoring facts buried in the middle."
                ),
                QuizQuestion(
                    id = 2,
                    question = "Why should at least 25-30% of the maximum token window be reserved during autonomous tool runs?",
                    options = listOf(
                        "To allow room for model reasoning chains, error recoveries, and JSON tool call outputs",
                        "Because cloud providers charge triple for full windows",
                        "To prevent the server from restarting",
                        "To enable dark mode rendering"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Autonomous agents generate significant token volume during internal reflection and structured tool parameters."
                )
            )
        )
    )

    val codingProblems = listOf(
        CodingProblem(
            id = "code-1",
            title = "Implement Agentic Reasoning Loop (OODA)",
            difficulty = "Intermediate",
            category = "Agent Development",
            description = "Write a function `runAgentLoop(goal, tools, maxTurns)` that receives a goal, simulates the OODA perception cycle, calls the required tool, and terminates when the goal condition is met.",
            starterCode = """// Panaversity Agentic Exercise 1
interface Tool {
  name: string;
  execute: (input: string) => string;
}

export function runAgentLoop(goal: string, tools: Tool[], maxTurns: number = 3): string {
  console.log("Observing goal:", goal);
  // TODO: Implement your OODA decision logic
  
  return "Goal Completed: " + goal;
}
""",
            expectedOutput = "Observing goal: Find Panaversity Resource\nGoal Completed: Find Panaversity Resource"
        ),
        CodingProblem(
            id = "code-2",
            title = "Context Window Sliding Token Buffer",
            difficulty = "Advanced",
            category = "Context Engineering",
            description = "Implement an algorithm that prunes older conversation turns while always preserving the system anchor prompt and the latest 2 user queries.",
            starterCode = """// Panaversity Context Buffer
export function pruneContext(systemPrompt: string, history: string[], maxTokens: number): string[] {
  // Always preserve systemPrompt
  // Keep newest history items until maxTokens reached
  return [systemPrompt, ...history.slice(-2)];
}
""",
            expectedOutput = "[System Prompt, User Query 3, Assistant Answer 3]"
        ),
        CodingProblem(
            id = "code-3",
            title = "Zod Spec Validator for Agent Action",
            difficulty = "Beginner",
            category = "Spec-Driven Development",
            description = "Validate incoming agent tool requests against strict action types: 'search_resource', 'execute_code', or 'summarize'.",
            starterCode = """// Validate action schema
export function validateAgentAction(actionType: string): boolean {
  const allowed = ["search_resource", "execute_code", "summarize"];
  return allowed.includes(actionType);
}
""",
            expectedOutput = "Action 'search_resource' is valid: true"
        )
    )

    val learningPathNodes = listOf(
        LearningPathNode("lp-1", "HTML5, CSS3 & Semantic Web", "COMPLETED", 100, "Verified in Quarter 1"),
        LearningPathNode("lp-2", "TypeScript Core & Strict OOP", "COMPLETED", 100, "Verified in Quarter 1"),
        LearningPathNode("lp-3", "Next.js 15 & Server Actions", "COMPLETED", 100, "Verified in Quarter 2"),
        LearningPathNode("lp-4", "Quarter 3: Agentic AI Foundations", "IN_PROGRESS", 72, "Current active program: Agent Factory & Context Eng"),
        LearningPathNode("lp-5", "Multi-Agent Swarms & LangGraph", "LOCKED", 0, "Unlocks after completing Chapter 6"),
        LearningPathNode("lp-6", "Cloud Native Microservices & Vector DBs", "LOCKED", 0, "Unlocks in Quarter 4"),
        LearningPathNode("lp-7", "Robotic AI & Physical Embodiment", "LOCKED", 0, "Capstone Specialization")
    )

    private val _communityPosts = MutableStateFlow(
        listOf(
            CommunityPost(
                id = "post-1",
                author = "Hamza Farooq",
                authorRole = "GIAIC Lead Fellow",
                timeAgo = "1 hour ago",
                title = "How are you handling token limits in Chapter 4 Context Engineering?",
                content = "Salam everyone! When processing long Panaversity GitBook markdown files, what strategy worked best for you? Did you use recursive chunking or hierarchical summarization?",
                category = "Discussions",
                likes = 34,
                commentsCount = 12,
                tags = listOf("ContextEngineering", "Tokens", "Panaversity")
            ),
            CommunityPost(
                id = "post-2",
                author = "Ayesha Siddiqui",
                authorRole = "Student (Karachi Center)",
                timeAgo = "3 hours ago",
                title = "Project Showcase: Panaversity Spec-Validator CLI Tool",
                content = "I built an open-source CLI that validates agentic schema files against Panaversity's 7 Principles automatically. Code is on GitHub, would love feedback from classmates!",
                category = "Project Showcase",
                likes = 58,
                commentsCount = 19,
                tags = listOf("Showcase", "TypeScript", "PanaversityTools")
            ),
            CommunityPost(
                id = "post-3",
                author = "Zubair Ahmed",
                authorRole = "Teaching Assistant",
                timeAgo = "Yesterday",
                title = "Next Class Alert: Live Q&A with Sir Zia Khan",
                content = "Reminder: Today at 7:00 PM we have a dedicated session covering General Agents vs Specialized Sub-Agents. Please review Chapter 3 official resources beforehand!",
                category = "Study Groups",
                likes = 120,
                commentsCount = 45,
                tags = listOf("Announcement", "LiveClass", "GeneralAgents")
            )
        )
    )
    val communityPosts: StateFlow<List<CommunityPost>> = _communityPosts.asStateFlow()

    private val _notifications = MutableStateFlow(
        listOf(
            NotificationItem(
                id = "notif-1",
                title = "Assignment Due Tomorrow",
                description = "'Build an Autonomous Agent Factory' is due tomorrow at 11:59 PM.",
                timeAgo = "2h ago",
                type = "ASSIGNMENT",
                isRead = false
            ),
            NotificationItem(
                id = "notif-2",
                title = "Live Class Reminder",
                description = "Class 'General Agents' starts at 7:00 PM today.",
                timeAgo = "4h ago",
                type = "SYSTEM",
                isRead = false
            ),
            NotificationItem(
                id = "notif-3",
                title = "Streak Maintained: 14 Days! 🔥",
                description = "You've logged in and completed lessons 14 days in a row!",
                timeAgo = "Yesterday",
                type = "STREAK",
                isRead = true
            ),
            NotificationItem(
                id = "notif-4",
                title = "New Panaversity Resource Available",
                description = "Official documentation on 'Agent Factory Paradigm' updated.",
                timeAgo = "2 days ago",
                type = "RESOURCE",
                isRead = true
            )
        )
    )
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _todayTasks = MutableStateFlow(
        listOf(
            TodayTask("t-1", "Read official resource: Agent Factory Paradigm", true, "Reading"),
            TodayTask("t-2", "Complete Chapter 3 lesson on General Agents", false, "Course"),
            TodayTask("t-3", "Take 4-question practice quiz on Agent Factory", false, "Quiz"),
            TodayTask("t-4", "Finish Assignment: Agent Factory with Tool Binding", false, "Assignment"),
            TodayTask("t-5", "Practice coding: OODA Reasoning Loop", false, "Coding Lab"),
            TodayTask("t-6", "Review weak topic: Token Budgeting & Memory Anchors", false, "Revision")
        )
    )
    val todayTasks: StateFlow<List<TodayTask>> = _todayTasks.asStateFlow()

    // Interactive operations
    fun toggleTask(taskId: String) {
        _todayTasks.update { list ->
            list.map { if (it.id == taskId) it.copy(isCompleted = !it.isCompleted) else it }
        }
    }

    fun toggleSaveResource(resourceId: String) {
        // Mock save toggle
    }

    fun toggleCompleteResource(resourceId: String) {
        // Mock complete toggle
    }

    fun toggleAssignmentStatus(id: String) {
        _assignments.update { list ->
            list.map {
                if (it.id == id) {
                    val nextStatus = if (it.status == AssignmentStatus.COMPLETED) AssignmentStatus.IN_PROGRESS else AssignmentStatus.COMPLETED
                    val nextProgress = if (nextStatus == AssignmentStatus.COMPLETED) 1.0f else 0.5f
                    it.copy(status = nextStatus, progress = nextProgress)
                } else it
            }
        }
    }

    fun addAssignment(title: String, course: String, dueDate: String, priority: AssignmentPriority, notes: String) {
        val newAsg = Assignment(
            id = "asg-${System.currentTimeMillis()}",
            title = title,
            course = course,
            dueDate = dueDate,
            priority = priority,
            progress = 0f,
            status = AssignmentStatus.PENDING,
            notes = notes
        )
        _assignments.update { listOf(newAsg) + it }
    }

    fun addNote(title: String, content: String, tags: List<String>, category: String, linkedResourceTitle: String? = null) {
        val newNote = SmartNote(
            id = "note-${System.currentTimeMillis()}",
            title = title,
            content = content,
            tags = tags,
            category = category,
            isFavorite = false,
            updatedAt = "Just now",
            linkedResourceTitle = linkedResourceTitle
        )
        _notes.update { listOf(newNote) + it }
    }

    fun deleteNote(noteId: String) {
        _notes.update { list -> list.filterNot { it.id == noteId } }
    }

    fun toggleLikeCommunityPost(postId: String) {
        _communityPosts.update { list ->
            list.map {
                if (it.id == postId) {
                    val liked = !it.isLiked
                    val newLikes = if (liked) it.likes + 1 else it.likes - 1
                    it.copy(isLiked = liked, likes = newLikes)
                } else it
            }
        }
    }

    fun addCommunityPost(title: String, content: String, category: String, tags: List<String>) {
        val newPost = CommunityPost(
            id = "post-${System.currentTimeMillis()}",
            author = studentProfile.name,
            authorRole = "Student (" + studentProfile.batch + ")",
            timeAgo = "Just now",
            title = title,
            content = content,
            category = category,
            likes = 1,
            commentsCount = 0,
            isLiked = true,
            tags = tags
        )
        _communityPosts.update { listOf(newPost) + it }
    }

    fun markAllNotificationsRead() {
        _notifications.update { list -> list.map { it.copy(isRead = true) } }
    }

    fun toggleNotificationRead(id: String) {
        _notifications.update { list ->
            list.map { if (it.id == id) it.copy(isRead = !it.isRead) else it }
        }
    }

    // AI Simulation logic for realistic student interactions & Roman Urdu explanations
    fun generateAiResponse(prompt: String, contextResource: PanaversityResource? = null): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("roman urdu") || lower.contains("urdu") -> {
                """
Assalam-o-Alaikum! 🌟 
Yeh topic bohot ahem aur interesting hai:

**Khulasa (Summary in Roman Urdu):**
GIAIC aur Panaversity ke is concept ka bunyadi maqsad yeh hai ke hum AI model ko sirf aam baaton tak mehdood na rakhein, balkay usay aik **Autonomous Agent** banayein jo tools chala sakay.

1. **Agent Factory**: Yeh aik aisi machine ya template hai jo har student ya task ke liye naya, clean agent tayyar karta hai taake purani baatein doosray task mein disturb na karein.
2. **Context Engineering**: Hamara token budget hamesha mehdood hota hai. Is liye aham rules sab se pehle aur aakhir mein rakhein.
3. **Panaversity Guideline**: Hamesha pehle schema (TypeScript/Zod) likhein, phir agent ko code karne dein!

Agar aapko mazeed misaal (example) chahiye tou bataiye!
                """.trimIndent()
            }
            lower.contains("explain simply") || lower.contains("beginner") || lower.contains("simple") -> {
                """
### 💡 Simplified Explanation (Beginner Friendly)

Imagine you are building a team of specialized workers. Instead of hiring one person who tries to do everything and gets confused:

1. **The Factory**: You have a blueprint. Whenever you need an assistant for coding, you press a button and instantiate a "Code Worker" with terminal tools.
2. **The Tools**: You hand this worker only the keys they need (safe sandboxing).
3. **Memory**: After the job is done, you save the notes and wipe the scratchpad so they stay fast and sharp.

In Panaversity, this is known as the **Agent Factory Paradigm**!
                """.trimIndent()
            }
            lower.contains("mcq") || lower.contains("quiz") || lower.contains("test") -> {
                """
### 🎯 Generated Practice MCQs (Based on Authorized Topic)

**Q1: What is the primary purpose of Context Engineering?**
- [A] To make the UI look aesthetic
- [B] To maximize relevant reasoning tokens and prevent context drift ✅
- [C] To bypass language model safety filters
- [D] To replace TypeScript with raw binary

**Q2: In Panaversity Spec-Driven Development, what comes first?**
- [A] Interface or Schema Specification ✅
- [B] Writing random conversational prompts
- [C] Running Docker deploy command
- [D] Creating YouTube tutorials

Would you like to add these to your Quiz practice deck?
                """.trimIndent()
            }
            lower.contains("flashcard") -> {
                """
### 📇 Generated Flashcards

**Card 1:**
- **Front:** What is the OODA loop in Agentic AI?
- **Back:** Observe, Orient, Decide, Act. The core 4-step decision-making loop for autonomous agents.

**Card 2:**
- **Front:** Why use Spec-Driven Development (SDD)?
- **Back:** Prevents unstructured hallucination by binding agent outputs to strongly-typed contracts (e.g., Zod schemas).

**Card 3:**
- **Front:** What is Context Drift?
- **Back:** The gradual loss of initial instructions as the conversation history grows too long.
                """.trimIndent()
            }
            lower.contains("summarize") || lower.contains("summary") -> {
                """
### 📋 Executive Summary
- **Domain**: Quarter 3 Certified Agentic AI (Panaversity Ecosystem)
- **Key Tenet**: Modular, specification-driven agent architecture with bounded execution loops.
- **Critical Action**: Decouple monolithic prompts into isolated supervisor and worker agents with explicit tool access.
- **Safety**: Apply the 7 Principles of Agentic Engineering (Idempotency, Observability, Least Privilege).
                """.trimIndent()
            }
            lower.contains("code") -> {
                """
### 💻 Code Analysis & Recommendation

Here is how to structure an Agent Factory in TypeScript conforming to Panaversity best practices:

```typescript
export class PanaversityAgentFactory {
  static createWorker(name: string, tools: string[]) {
    return {
      agentId: crypto.randomUUID(),
      name,
      tools,
      createdAt: new Date().toISOString(),
      execute: async (prompt: string) => {
        // Executes with isolated context window
        return `[Worker: ${'$'}{name}] processed: ${'$'}{prompt}`;
      }
    };
  }
}
```
**Safety Note**: Always sanitize tool inputs and enforce rate limits!
                """.trimIndent()
            }
            else -> {
                """
Hello Muhammad Afnan! I am your **AI Study Assistant** for GIAIC & Panaversity.

I am actively grounded in your Quarter 3 curriculum:
- **Agent Factory Paradigm** (Chapter 1)
- **Context Engineering & Memory Anchors** (Chapter 4)
- **Spec-Driven Development** (Chapter 5)
- **Panaversity 7 Principles** (Chapter 6)

How can I help your study session right now? You can ask me to:
- Explain any concept in **Roman Urdu** or beginner terms
- Generate **MCQs** or **Flashcards**
- Review your **code** or homework assignment
- Break down complex **Panaversity specifications**
                """.trimIndent()
            }
        }
    }
}
