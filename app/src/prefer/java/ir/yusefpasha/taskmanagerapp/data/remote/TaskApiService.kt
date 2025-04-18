package ir.yusefpasha.taskmanagerapp.data.remote

import ir.yusefpasha.taskmanagerapp.domain.utils.convertLocalDateTimeToMillisecond
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class TaskApiService {

    suspend fun getTasks(): List<TaskDto> {
        delay(Random.nextInt(from = 1000, until = 2000).milliseconds)
        val tasks = List(1) {
            generateFakeTask()
        }
        return tasks
    }

    private fun generateFakeTask(): TaskDto {
        val titles = listOf(
            "Morning Routine Checklist",
            "Grocery Shopping List",
            "Weekly Team Meeting Notes",
            "Summer Vacation Plans",
            "Favorite Book Recommendations",
            "Fitness Goals for This Month",
            "Daily Task Tracker",
            "Home Renovation Ideas",
            "Monthly Budget Overview",
            "Weekend Movie Watchlist"
        )
        val subtitles = listOf(
            "Start the day by waking up early, doing a light stretch, showering, eating a healthy breakfast, and planning the top tasks before heading out.",
            "Buy fresh vegetables, fruits, bread, eggs, and milk. Don't forget snacks and cleaning supplies. Stick to the budget and avoid impulse purchases.",
            "Review last week's progress, assign new tasks, discuss roadblocks, and confirm deadlines. Everyone should leave with clear action points and goals.",
            "Book flights and hotels in advance. Make a list of places to visit, restaurants to try, and souvenirs to bring back from the trip.",
            "Include a mix of genres like thrillers, non-fiction, and science fiction. Each book should have a compelling storyline and memorable characters.",
            "Focus on daily workouts, improve endurance, eat healthy meals, drink enough water, and track your progress with weekly summaries and photos.",
            "Use this list to stay organized throughout the day by noting down important tasks, small goals, and reminders you might forget later.",
            "List ideas for redesigning the living room, kitchen, or bedroom. Include color schemes, furniture changes, and budget estimates for each section.",
            "Track monthly income, expenses, and savings. Identify areas where you can reduce spending and make plans to achieve your financial goals.",
            "Pick a mix of genres like action, comedy, or drama. Include both new releases and classic favorites that are fun to re-watch with friends."
        )
        val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val deadline = LocalDateTime(
            year = currentDateTime.year,
            monthNumber = currentDateTime.monthNumber,
            dayOfMonth = currentDateTime.dayOfMonth,
            hour = Random.nextInt(from = 1, until = 15),
            minute = Random.nextInt(from = 0, until = 59),
        )
        return TaskDto(
            title = titles.random(),
            description = subtitles.random(),
            deadline = convertLocalDateTimeToMillisecond(deadline)
        )
    }

}