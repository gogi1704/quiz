package com.quizApp.data.level


sealed class SportChecker {
    class Football : SportChecker()
    class BasketBall : SportChecker()
    class Hockey : SportChecker()
    class UsaFootball : SportChecker()

}
data class SportCheckerModel(
    val sportType: SportChecker
)