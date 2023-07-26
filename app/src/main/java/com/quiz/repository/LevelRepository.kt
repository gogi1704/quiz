package com.quizApp.repository

import android.app.Application
import android.content.Context
import android.os.Build.VERSION_CODES.O
import android.util.Log
import com.quizApp.data.db.LevelDao
import com.quizApp.data.db.QuestionsDao
import com.quizApp.data.db.entities.LevelEntity
import com.quizApp.data.db.entities.QuestionsEntity
import com.quizApp.data.db.entities.STATUS_COMPLETED
import com.quizApp.data.db.entities.STATUS_LOCK
import com.quizApp.data.db.entities.STATUS_STARTED
import com.quizApp.data.db.entities.TYPE_BASKETBALL
import com.quizApp.data.db.entities.TYPE_FOOTBALL
import com.quizApp.data.db.entities.TYPE_HOCKEY
import com.quizApp.data.db.entities.TYPE_USA_FOOTBALL
import com.quizApp.data.level.AnswerModel
import com.quizApp.data.level.LevelData
import com.quizApp.data.level.LevelModel
import com.quizApp.data.level.QuestionModel
import com.quizApp.data.level.SportChecker
import com.quizApp.data.level.StatusState
import javax.inject.Inject

interface LevelRepository {
    suspend fun getFootBallLevels(): LevelModel
    suspend fun getBasketBallLevels(): LevelModel
    suspend fun getHockeyLevels(): LevelModel
    suspend fun getUsaFootballLevels(): LevelModel
    suspend fun firstStart()
    suspend fun getQuestionsByLevelId(id: Int): List<QuestionModel>
    suspend fun answerTrue(levelId: Int)
}

class LevelRepositoryImpl @Inject constructor(
    private val questionsDao: QuestionsDao,
    private val levelsDAo: LevelDao,
    application: Application
) :
    LevelRepository {

    private val pref = application.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
    private var isFirstStart = pref.getBoolean("IS_FIRST", true)

    init {
        if (isFirstStart) {
            isFirstStart = false
            pref.edit()
                .putBoolean("IS_FIRST", false)
                .apply()

        }

    }

    override suspend fun firstStart() {
        levelsDAo.insertLevels(putLevels())
        questionsDao.insertQuestions(putData())
    }

    override suspend fun getFootBallLevels(): LevelModel {
        return LevelModel(
            typeSport = SportChecker.Football(),
            list = levelsDAo.getFootballLevels().map {
                LevelData(
                    it.id,
                    it.name,
                    statusState = when (it.status) {
                        STATUS_LOCK -> {
                            StatusState.Lock
                        }

                        STATUS_COMPLETED -> {
                            StatusState.Complete
                        }

                        else -> {
                            StatusState.Started
                        }
                    },
                    countCompleted = it.countCompleted
                )
            }

        )

    }

    override suspend fun getBasketBallLevels(): LevelModel {
        return LevelModel(
            typeSport = SportChecker.BasketBall(),
            list = levelsDAo.getBasketBallLevels().map {
                LevelData(
                    it.id,
                    it.name,
                    statusState = when (it.status) {
                        STATUS_LOCK -> {
                            StatusState.Lock
                        }

                        STATUS_COMPLETED -> {
                            StatusState.Complete
                        }

                        else -> {
                            StatusState.Started
                        }
                    },
                    countCompleted = it.countCompleted
                )
            }

        )
    }

    override suspend fun getHockeyLevels(): LevelModel {
        return LevelModel(
            typeSport = SportChecker.Hockey(),
            list = levelsDAo.getHockeyLevels().map {
                LevelData(
                    it.id,
                    it.name,
                    statusState = when (it.status) {
                        STATUS_LOCK -> {
                            StatusState.Lock
                        }

                        STATUS_COMPLETED -> {
                            StatusState.Complete
                        }

                        else -> {
                            StatusState.Started
                        }
                    },
                    countCompleted = it.countCompleted
                )
            }

        )
    }

    override suspend fun getUsaFootballLevels(): LevelModel {
        return LevelModel(
            typeSport = SportChecker.UsaFootball(),
            list = levelsDAo.getUsaFootballLevels().map {
                LevelData(
                    it.id,
                    it.name,
                    statusState = when (it.status) {
                        STATUS_LOCK -> {
                            StatusState.Lock
                        }

                        STATUS_COMPLETED -> {
                            StatusState.Complete
                        }

                        else -> {
                            StatusState.Started
                        }
                    },
                    countCompleted = it.countCompleted
                )
            }

        )
    }

    private fun putLevels(): List<LevelEntity> = listOf(
        LevelEntity(
            type = TYPE_FOOTBALL,
            name = "Pro",
            countCompleted = 0
        ),
        LevelEntity(
            type = TYPE_FOOTBALL,
            name = "Medium",
            countCompleted = 0
        ),
        LevelEntity(
            type = TYPE_FOOTBALL,
            name = "Low",
            countCompleted = 0,
            status = STATUS_STARTED
        ),
        LevelEntity(
            type = TYPE_BASKETBALL,
            name = "Pro",
            countCompleted = 0
        ),
        LevelEntity(
            type = TYPE_BASKETBALL,
            name = "Medium",
            countCompleted = 0
        ),
        LevelEntity(
            type = TYPE_BASKETBALL,
            name = "Low",
            countCompleted = 0,
            status = STATUS_STARTED

        ),
        LevelEntity(
            type = TYPE_HOCKEY,
            name = "Pro",
            countCompleted = 0
        ),
        LevelEntity(
            type = TYPE_HOCKEY,
            name = "Medium",
            countCompleted = 0
        ),
        LevelEntity(
            type = TYPE_HOCKEY,
            name = "Low",
            countCompleted = 0,
            status = STATUS_STARTED

        ),
        LevelEntity(
            type = TYPE_USA_FOOTBALL,
            name = "Pro",
            countCompleted = 0
        ),
        LevelEntity(
            type = TYPE_USA_FOOTBALL,
            name = "Medium",
            countCompleted = 0
        ),
        LevelEntity(
            type = TYPE_USA_FOOTBALL,
            name = "Low",
            countCompleted = 0,
            status = STATUS_STARTED

        ),
    )

    private fun putData(): List<QuestionsEntity> =
        listOf(
            QuestionsEntity(
                type = TYPE_FOOTBALL, title = "How many players are on a football team?",
                answers = listOf(
                    AnswerModel("9"), AnswerModel("10"), AnswerModel("11", true), AnswerModel("12"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL, title = "What is the shape of a football field?",
                answers = listOf(
                    AnswerModel("Circle "),
                    AnswerModel("Rectangle ", true),
                    AnswerModel("Square "),
                    AnswerModel("Triangle"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL, title = "How long is a standard football match?",
                answers = listOf(
                    AnswerModel("90 minutes ", true),
                    AnswerModel("120 minutes"),
                    AnswerModel("45 minutes"),
                    AnswerModel("100 minutes"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the international governing body of football?",
                answers = listOf(
                    AnswerModel("FIFA", true),
                    AnswerModel("UEFA"),
                    AnswerModel("NFL"),
                    AnswerModel("NBA"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the trophy awarded to the winners of the FIFA World Cup?",
                answers = listOf(
                    AnswerModel("The Champions Trophy "),
                    AnswerModel("The World Cup Trophy"),
                    AnswerModel("*The FIFA World Cup Trophy", true),
                    AnswerModel("The Golden Boot"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL, title = "Which country has won the most FIFA World Cups?",
                answers = listOf(
                    AnswerModel("Argentina"),
                    AnswerModel("Germany"),
                    AnswerModel("Brazil", true),
                    AnswerModel("Italy"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the most prestigious club competition in European football?",
                answers = listOf(
                    AnswerModel("The Europa League"),
                    AnswerModel("The Champions League", true),
                    AnswerModel("The Super Cup"),
                    AnswerModel("The Premier League"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "Who is the all-time top scorer in the UEFA Champions League?",
                answers = listOf(
                    AnswerModel("Lionel Messi", true),
                    AnswerModel("Cristiano Ronaldo"),
                    AnswerModel("Raul Gonzalez Blanco"),
                    AnswerModel("Karim Benzema"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the offside rule in football?",
                answers = listOf(
                    AnswerModel("Offside", true),
                    AnswerModel("Inside"),
                    AnswerModel("Backside"),
                    AnswerModel("Frontside"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the maximum number of substitutions allowed in a football match?",
                answers = listOf(
                    AnswerModel("None"),
                    AnswerModel("3", true),
                    AnswerModel("Unlimited"),
                    AnswerModel("Depends on the referee"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the first football club in the world? ",
                answers = listOf(
                    AnswerModel("Manchester United"),
                    AnswerModel("Real Madrid"),
                    AnswerModel(" AC Milan"),
                    AnswerModel("Sheffield", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is an own goal in football?",
                answers = listOf(
                    AnswerModel("A goal scored by a player for his own team"),
                    AnswerModel("A goal scored by a player for the opposing team"),
                    AnswerModel("A goal scored by a player against his own team ", true),
                    AnswerModel("A goal scored by a player from outside the penalty area"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the technique where players jump to head the ball?",
                answers = listOf(
                    AnswerModel("Heading", true),
                    AnswerModel("Jumping"),
                    AnswerModel("Flying"),
                    AnswerModel("Leaping"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the position responsible for stopping opposing players from scoring goals? a.  b.  c.  d. *",
                answers = listOf(
                    AnswerModel("Midfielder"),
                    AnswerModel("Forward"),
                    AnswerModel("Striker"),
                    AnswerModel("Defender", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is an indirect free kick in football? ",
                answers = listOf(
                    AnswerModel("A free kick that must be taken quickly"),
                    AnswerModel("A free kick that must be taken from outside the penalty area"),
                    AnswerModel(
                        "A free kick where another player must touch the ball before it can be shot at goal",
                        true
                    ),
                    AnswerModel("A free kick where only one player can touch the ball before it can be shot at goal."),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is an assist in football?",
                answers = listOf(
                    AnswerModel("The act of stopping an opposing player from scoring a goal"),
                    AnswerModel("The act of scoring a goal"),
                    AnswerModel("The act of saving or blocking an opposing player’s shot on goal"),
                    AnswerModel(
                        "The act of passing or setting up another player to score a goal ",
                        true
                    ),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is offside trap in football?",
                answers = listOf(
                    AnswerModel("A defensive tactic where defenders move forward to catch opposing players offside"),
                    AnswerModel("A defensive tactic where defenders move backward to catch opposing players offside"),
                    AnswerModel(
                        "A defensive tactic where defenders move forward in unison to catch opposing players offside",
                        true
                    ),
                    AnswerModel("A defensive tactic where defenders move backward in unison to catch opposing players offside"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is tiki-taka style of play in football? ",
                answers = listOf(
                    AnswerModel("A style of play characterized by long balls and aerial duels"),
                    AnswerModel(
                        "A style of play characterized by short passing and movement working the ball through various channels and maintaining possession",
                        true
                    ),
                    AnswerModel("A style of play characterized by physicality and aggression"),
                    AnswerModel("A style of play characterized by counter-attacking and directness"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is nutmeg in football?",
                answers = listOf(
                    AnswerModel("The act of scoring from outside the penalty area"),
                    AnswerModel("The act of dribbling past an opponent with speed"),
                    AnswerModel(
                        "The act of passing or kicking the ball between an opponent’s legs",
                        true
                    ),
                    AnswerModel("The act of shooting with power and accuracy"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is Panenka penalty kick in football?",
                answers = listOf(
                    AnswerModel(
                        "A penalty kick taken with deception where the player chips the ball down the center of the goal as the goalkeeper dives to one side",
                        true
                    ),
                    AnswerModel("A penalty kick taken with power and accuracy."),
                    AnswerModel("A penalty kick taken with speed and swerve"),
                    AnswerModel("A penalty kick taken with a backheel or rabona"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the first official international football match?",
                answers = listOf(
                    AnswerModel("England vs Scotland"),
                    AnswerModel("Scotland vs England", true),
                    AnswerModel("Brazil vs Argentina"),
                    AnswerModel("Germany vs Italy"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the first footballer to score a hat-trick in a World Cup final?",
                answers = listOf(
                    AnswerModel("Pele"),
                    AnswerModel("Diego Maradona"),
                    AnswerModel("Zinedine Zidane"),
                    AnswerModel("Geoff Hurst", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is total football in football?",
                answers = listOf(
                    AnswerModel("A style of play characterized by physicality and aggression"),
                    AnswerModel("A style of play characterized by counter-attacking and directness"),
                    AnswerModel(
                        "A style of play characterized by fluid movement and interchangeability of positions where any outfield player can take over the role of any other player in the team",
                        true
                    ),
                    AnswerModel("A style of play characterized by long balls and aerial duels"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the first footballer to win three Ballon d’Or awards?",
                answers = listOf(
                    AnswerModel("Johan Cruyff", true),
                    AnswerModel("Lionel Messi"),
                    AnswerModel("Cristiano Ronaldo"),
                    AnswerModel("Michel Platini"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_FOOTBALL,
                title = "What is the name of the first footballer to score 1000 career goals? ",
                answers = listOf(
                    AnswerModel("Cristiano Ronaldo"),
                    AnswerModel("Lionel Messi"),
                    AnswerModel("Ferenc Puskas"),
                    AnswerModel("Pele", true),
                ),
            ),

            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the professional basketball league in the United States?",
                answers = listOf(
                    AnswerModel("NBA", true),
                    AnswerModel("NFL"),
                    AnswerModel("MLB"),
                    AnswerModel("NHL"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "How many players are on a basketball team? ",
                answers = listOf(
                    AnswerModel("5", true),
                    AnswerModel("6"),
                    AnswerModel("7"),
                    AnswerModel("8"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the trophy awarded to the NBA champion? ",
                answers = listOf(
                    AnswerModel("Vince Lombardi Trophy"),
                    AnswerModel("Larry O’Brien Trophy", true),
                    AnswerModel("Commissioner’s Trophy"),
                    AnswerModel("Stanley Cup"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "Who is considered one of the greatest basketball players of all time and played for the Chicago Bulls? ",
                answers = listOf(
                    AnswerModel(" LeBron James"),
                    AnswerModel("Kobe Bryant"),
                    AnswerModel("Michael Jordan", true),
                    AnswerModel("Shaquille O’Neal"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the line that separates the frontcourt from the backcourt?",
                answers = listOf(
                    AnswerModel("Half-court line", true),
                    AnswerModel("Three-point line"),
                    AnswerModel(" Free-throw line"),
                    AnswerModel("Baseline"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "How high is an official basketball hoop from the ground? ",
                answers = listOf(
                    AnswerModel("8 feet (2.44 meters)"),
                    AnswerModel("9 feet (2.74 meters)"),
                    AnswerModel("10 feet (3.05 meters)", true),
                    AnswerModel("1 feet (3.35 meters)"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "How many points is a free throw worth? ",
                answers = listOf(
                    AnswerModel("1 point", true),
                    AnswerModel("2 points"),
                    AnswerModel("3 points"),
                    AnswerModel("4 points"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the violation when a player takes too many steps without dribbling the ball?",
                answers = listOf(
                    AnswerModel("Traveling", true),
                    AnswerModel("Double dribble"),
                    AnswerModel("Carrying"),
                    AnswerModel("Palming"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "Who was the first player to score 100 points in a single NBA game?",
                answers = listOf(
                    AnswerModel(" Wilt Chamberlain", true),
                    AnswerModel(" Michael Jordan"),
                    AnswerModel(" Kobe Bryant"),
                    AnswerModel("LeBron James"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the defensive strategy where a team assigns one player to guard the opposing team’s best player? ",
                answers = listOf(
                    AnswerModel("Man-to-man defense"),
                    AnswerModel("Zone defense"),
                    AnswerModel("Box-and-one defense", true),
                    AnswerModel("Triangle-and-two defense"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the violation when a player touches the ball with their hand while it is on its downward flight towards the basket?",
                answers = listOf(
                    AnswerModel("Goaltending", true),
                    AnswerModel("Basket interference"),
                    AnswerModel("Over-and-back "),
                    AnswerModel("Three seconds"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the violation when an offensive player remains in the key for more than three seconds? ",
                answers = listOf(
                    AnswerModel("Goaltending"),
                    AnswerModel("Basket interference"),
                    AnswerModel("Over-and-back"),
                    AnswerModel("Three seconds", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the violation when a player crosses the half-court line and then returns to the backcourt with the ball?",
                answers = listOf(
                    AnswerModel("Goaltending"),
                    AnswerModel("Basket interference"),
                    AnswerModel("Over-and-back", true),
                    AnswerModel("Three seconds"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the shot where a player jumps and releases the ball near the top of their jump? ",
                answers = listOf(
                    AnswerModel("Layup"),
                    AnswerModel("Dunk"),
                    AnswerModel("Jump shot", true),
                    AnswerModel("Hook shot "),
                ),
            ),

            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the shot where a player releases the ball with one hand while jumping towards the basket? ",
                answers = listOf(
                    AnswerModel("Layup"),
                    AnswerModel("Dunk", true),
                    AnswerModel("Jump shot"),
                    AnswerModel("Hook shot "),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the violation when a player dribbles the ball stops and then starts dribbling again?",
                answers = listOf(
                    AnswerModel("Traveling"),
                    AnswerModel(" Double dribble ", true),
                    AnswerModel("Carrying"),
                    AnswerModel("Palming "),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the shot where a player releases the ball with one hand while their arm is fully extended? ",
                answers = listOf(
                    AnswerModel("Layup"),
                    AnswerModel("Dunk"),
                    AnswerModel("Jump shot"),
                    AnswerModel("Hook shot ", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the pass where a player throws the ball over their head with both hands to their teammate?",
                answers = listOf(
                    AnswerModel("Chest pass"),
                    AnswerModel("Bounce pass"),
                    AnswerModel("Overhead pass"),
                    AnswerModel("Behind-the-back pass ", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the pass where a player wraps the ball around their back to their teammate? ",
                answers = listOf(
                    AnswerModel(" Chest pass"),
                    AnswerModel("Bounce pass"),
                    AnswerModel("Overhead pass"),
                    AnswerModel("Behind-the-back pass ", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the shot where a player releases the ball with one hand while jumping towards the basket? ",
                answers = listOf(
                    AnswerModel("Layup"),
                    AnswerModel("Dunk"),
                    AnswerModel("Jump shot", true),
                    AnswerModel("Hook shot "),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the violation when a defensive player makes contact with an offensive player who is in the act of shooting? ",
                answers = listOf(
                    AnswerModel("Blocking"),
                    AnswerModel("Charging"),
                    AnswerModel("Shooting foul"),
                    AnswerModel("Technical foul ", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the violation when an offensive player makes contact with a defensive player who has established position?",
                answers = listOf(
                    AnswerModel("Blocking"),
                    AnswerModel("Shooting foul", true),
                    AnswerModel("Charging"),
                    AnswerModel("Technical foul "),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the violation when a player uses their body to impede the movement of an opponent without making an attempt to play the ball? ",
                answers = listOf(
                    AnswerModel("Blocking", true),
                    AnswerModel("Charging"),
                    AnswerModel("Shooting foul "),
                    AnswerModel("Technical foul "),
                ),
            ),
            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "What is the name of the violation when a player or coach commits an unsportsmanlike act? ",
                answers = listOf(
                    AnswerModel("Blocking"),
                    AnswerModel("Charging"),
                    AnswerModel("Shooting foul "),
                    AnswerModel("Technical foul", true),
                ),
            ),

            QuestionsEntity(
                type = TYPE_BASKETBALL,
                title = "Who was the first player to score 100 points in a single NBA game?",
                answers = listOf(
                    AnswerModel(" Wilt Chamberlain", true),
                    AnswerModel(" Michael Jordan"),
                    AnswerModel(" Kobe Bryant"),
                    AnswerModel("LeBron James"),
                ),
            ),

            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the trophy awarded to the winner of the National Hockey League (NHL) playoffs? ",
                answers = listOf(
                    AnswerModel("Stanley Cup", true),
                    AnswerModel(" Lombardi Trophy"),
                    AnswerModel("Commissioner’s Trophy"),
                    AnswerModel("Larry O’Brien Trophy"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "How many players from each team are allowed on the ice at the same time during a hockey game?",
                answers = listOf(
                    AnswerModel("4"),
                    AnswerModel("6", true),
                    AnswerModel(" 7"),
                    AnswerModel("8")
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the penalty where a player must leave the ice for five minutes?  ",
                answers = listOf(
                    AnswerModel(" Minor penalty"),
                    AnswerModel(" Double minor penalty"),
                    AnswerModel("Major penalty", true),
                    AnswerModel("Misconduct penalty"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the term used when a team has more players on the ice than their opponent due to a penalty?",
                answers = listOf(
                    AnswerModel("Power play", true),
                    AnswerModel("Short-handed"),
                    AnswerModel("Man advantage"),
                    AnswerModel("Penalty kill"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the area where players sit while serving a penalty?",
                answers = listOf(
                    AnswerModel("Penalty area"),
                    AnswerModel(" Sin bin"),
                    AnswerModel(" Penalty box", true),
                    AnswerModel(" Holding cell"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the term used when a player scores three goals in one game?",
                answers = listOf(
                    AnswerModel("Hat trick", true),
                    AnswerModel("Triple play"),
                    AnswerModel("Three-peat"),
                    AnswerModel("Holding cell"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the line that divides the ice rink in half?",
                answers = listOf(
                    AnswerModel(" Center line ", true),
                    AnswerModel(" Blue line"),
                    AnswerModel(" Goal line"),
                    AnswerModel(" Black line"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the curved stick used by players to shoot pass and carry the puck?",
                answers = listOf(
                    AnswerModel("Bat "),
                    AnswerModel("Club "),
                    AnswerModel("Racquet "),
                    AnswerModel("Hockey stick", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the protective gear worn by players to protect their head and face?",
                answers = listOf(
                    AnswerModel(" Helmet ", true),
                    AnswerModel(" Mask "),
                    AnswerModel(" Visor"),
                    AnswerModel(" Hat"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the rubber disk that players try to shoot into the opposing team’s net?",
                answers = listOf(
                    AnswerModel("Ball "),
                    AnswerModel("Puck ", true),
                    AnswerModel("Disk "),
                    AnswerModel("Frisbee"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the official who drops the puck to start play at the beginning of each period and after goals and penalties?",
                answers = listOf(
                    AnswerModel("Linesman"),
                    AnswerModel(" Referee", true),
                    AnswerModel("Umpire"),
                    AnswerModel("Judge"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the trophy awarded to the NHL’s most valuable player?",
                answers = listOf(
                    AnswerModel("Hart Memorial Trophy ", true),
                    AnswerModel("Art Ross Trophy"),
                    AnswerModel("Lady Byng Memorial Trophy"),
                    AnswerModel("Conn Smythe Trophy"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Which team won the Stanley Cup in 2021",
                answers = listOf(
                    AnswerModel("Tampa Bay Lightning "),
                    AnswerModel("Montreal Canadiens", true),
                    AnswerModel("Vegas Golden Knights"),
                    AnswerModel("New York Islanders"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Who holds the record for most goals scored in a single NHL season?",
                answers = listOf(
                    AnswerModel(" Wayne Gretzky ", true),
                    AnswerModel(" Mario Lemieux "),
                    AnswerModel(" Bobby Orr"),
                    AnswerModel(" Gordie Howe"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Who is the all-time leading scorer in NHL history?",
                answers = listOf(
                    AnswerModel(" Gordie Howe"),
                    AnswerModel(" Bobby Orr"),
                    AnswerModel(" Mario Lemieux"),
                    AnswerModel("Wayne Gretzky", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the trophy awarded to the NHL’s top rookie?",
                answers = listOf(
                    AnswerModel(" Calder Memorial Trophy ", true),
                    AnswerModel("  Hart Memorial Trophy "),
                    AnswerModel(" Art Ross Trophy"),
                    AnswerModel(" Conn Smythe Trophy"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Which team has won the most Stanley Cup championships?",
                answers = listOf(
                    AnswerModel("Toronto Maple Leafs"),
                    AnswerModel("Detroit Red Wing"),
                    AnswerModel("Montreal Canadiens", true),
                    AnswerModel("Boston Bruins"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "What is the name of the trophy awarded to the NHL’s top defenseman?",
                answers = listOf(
                    AnswerModel("Norris Trophy", true),
                    AnswerModel(" Hart Memorial Trophy"),
                    AnswerModel(" Art Ross Trophy "),
                    AnswerModel("Conn Smythe Trophy"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Who holds the record for most points scored in a single NHL season?",
                answers = listOf(
                    AnswerModel("Mario Lemieux"),
                    AnswerModel("Bobby Or"),
                    AnswerModel("Wayne Gretzky", true),
                    AnswerModel("Gordie Howe"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Who was the first player to score 50 goals in one season?",
                answers = listOf(
                    AnswerModel(" Maurice Richard"),
                    AnswerModel(" Wayne Gretzky", true),
                    AnswerModel("Brett Hull"),
                    AnswerModel("Phil Esposito"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Who was the first American-born player to score 500 goals in his NHL career?",
                answers = listOf(
                    AnswerModel(" Mike Modano"),
                    AnswerModel(" Jeremy Roenick"),
                    AnswerModel(" Pat LaFontaine"),
                    AnswerModel("Joe Mullen", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Who holds the record for most goals scored in a single NHL game?",
                answers = listOf(
                    AnswerModel(" Wayne Gretzky"),
                    AnswerModel(" Mario Lemieux"),
                    AnswerModel(" Joe Malone", true),
                    AnswerModel(" Gordie Howe"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Who is the only player to win the Hart Trophy as league MVP more than 3 times in a row?",
                answers = listOf(
                    AnswerModel(" Wayne Gretzky", true),
                    AnswerModel(" Gordie Howe"),
                    AnswerModel(" Bobby Orr"),
                    AnswerModel(" Guy Lafleur"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Who is the only player to score 50 goals in a season with two different teams?",
                answers = listOf(
                    AnswerModel(" Wayne Gretzky"),
                    AnswerModel(" Brett Hull", true),
                    AnswerModel(" Mario Lemieux"),
                    AnswerModel(" Phil Esposito"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_HOCKEY,
                title = "Who is the only player to win the Norris Trophy as best defenseman more than 5 times in a row?",
                answers = listOf(
                    AnswerModel(" Bobby Orr"),
                    AnswerModel(" Nicklas Lidstrom", true),
                    AnswerModel(" Doug Harvey"),
                    AnswerModel("Ray Bourque"),
                ),
            ),

            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the territory of the defensive team in American football?",
                answers = listOf(
                    AnswerModel(" End zone"),
                    AnswerModel(" Touchdown zone"),
                    AnswerModel(" Defense zone"),
                    AnswerModel(" Red zone", true),
                ),
            ),


            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the game in which the team tries to score the ball into the “end zone” of the opponent?",
                answers = listOf(
                    AnswerModel(" Touchdown ", true),
                    AnswerModel(" Field goal"),
                    AnswerModel(" Extra point"),
                    AnswerModel(" Safety"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "How many points does a team get if they make a successful “field goal”?",
                answers = listOf(
                    AnswerModel(" 3 points", true),
                    AnswerModel(" 6 points"),
                    AnswerModel(" 1 point"),
                    AnswerModel(" 2 points"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of throwing the ball forward in American football?",
                answers = listOf(
                    AnswerModel(" Pass ", true),
                    AnswerModel(" Tackle "),
                    AnswerModel(" Kickoff "),
                    AnswerModel(" Punt"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "How long is one quarter in American football?",
                answers = listOf(
                    AnswerModel(" 10 minutes "),
                    AnswerModel(" 15 minutes", true),
                    AnswerModel(" 20 minutes"),
                    AnswerModel(" 25 minutes"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "How many “downs” (attempts to move the ball 10 yards) does the team have to get the first 10 yards?",
                answers = listOf(
                    AnswerModel(" 1 down"),
                    AnswerModel(" 2 downs"),
                    AnswerModel(" 3 downs"),
                    AnswerModel(" 4 downs", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "Which player is responsible for protecting his team from passing the ball to the opponent?",
                answers = listOf(
                    AnswerModel(" Quarterback ", true),
                    AnswerModel(" Running back"),
                    AnswerModel(" Wide receiver"),
                    AnswerModel(" Cornerback"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of throwing the ball forward in American football?",
                answers = listOf(
                    AnswerModel(" Pass ", true),
                    AnswerModel(" Tackle "),
                    AnswerModel(" Kickoff "),
                    AnswerModel(" Punt"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the player whose main task is to fulfill the “field goal”?",
                answers = listOf(
                    AnswerModel(" Wide receiver "),
                    AnswerModel(" Quarterback "),
                    AnswerModel(" Running back"),
                    AnswerModel(" Kicker", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "Who is known as the “Father of American Football”?  ",
                answers = listOf(
                    AnswerModel(" Walter Camp", true),
                    AnswerModel(" John Heisman"),
                    AnswerModel(" Amos Alonzo"),
                    AnswerModel(" Pop Warner"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the first intercollegiate football contest in New Brunswick New Jersey? ",
                answers = listOf(
                    AnswerModel(" Princeton vs. Rutgers", true),
                    AnswerModel(" Harvard vs. Yale"),
                    AnswerModel(" Brown vs. Dartmouth"),
                    AnswerModel(" Columbia vs. Cornell"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the sport that American football evolved from? ",
                answers = listOf(
                    AnswerModel(" Hockey and soccer "),
                    AnswerModel(" Cricket and soccer"),
                    AnswerModel(" Baseball and soccer"),
                    AnswerModel(" Rugby and soccer", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the organization that made two key innovations to the fledgling game of American football? ",
                answers = listOf(
                    AnswerModel(" National Football League"),
                    AnswerModel(" American Football League"),
                    AnswerModel(" Intercollegiate Football Association", true),
                    AnswerModel(" Canadian Football League"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the position that Walter Camp introduced in American football? ",
                answers = listOf(
                    AnswerModel(" Quarterback ", true),
                    AnswerModel(" Halfback "),
                    AnswerModel(" Fullback "),
                    AnswerModel(" Wide receiver"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the scoring scale used in American football today? ",
                answers = listOf(
                    AnswerModel(" Goal  penalty kick   corner kick   free kick"),
                    AnswerModel(" Point guard    center    forward   shooting guard"),
                    AnswerModel(" Strikeout, home run, RBI, stolen base"),
                    AnswerModel(" Touchdown   field goal    safety  extra point", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the organization that was founded in Canton Ohio in 1920? ",
                answers = listOf(
                    AnswerModel(" American Football League"),
                    AnswerModel(" Intercollegiate Football Association"),
                    AnswerModel(
                        " American Professional Football Association (APFA), later known as National Football League (NFL) (correct) ",
                        true
                    ),
                    AnswerModel(" Canadian Football League"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "Who is the only ",
                answers = listOf(
                    AnswerModel(" Orr"),
                    AnswerModel(" Lidstrom", true),
                    AnswerModel(" Harvey"),
                    AnswerModel(" Bourque"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the sport that Harvard University maintained its distance by sticking to a rugby-soccer hybrid called “Boston Game”? ",
                answers = listOf(
                    AnswerModel(" Cricket and soccer"),
                    AnswerModel(" Rugby and soccer", true),
                    AnswerModel(" Baseball and soccer"),
                    AnswerModel(" Hockey and soccer"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the sport that Yale players and spectators embraced as well after playing their first intercollegiate match against Harvard University? ",
                answers = listOf(
                    AnswerModel(" Rugby and soccer", true),
                    AnswerModel(" Cricket and soccer"),
                    AnswerModel(" Baseball and soccer"),
                    AnswerModel(" Hockey and soccer"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "What is the name of the sport that closely related to two ancient English sports - rugby and soccer? ",
                answers = listOf(
                    AnswerModel(" Hockey"),
                    AnswerModel(" Baseball "),
                    AnswerModel(" Cricket "),
                    AnswerModel(" Gridiron football or American football", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "WWho is the only player in NFL history to win five Super Bowl MVP awards?  ",
                answers = listOf(
                    AnswerModel(" Tom Brady", true),
                    AnswerModel(" Joe Montana"),
                    AnswerModel(" Terry Bradshaw"),
                    AnswerModel(" Bart Starr"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "Who is the only player in NFL history to rush for over 2000 yards in a single season?  ",
                answers = listOf(
                    AnswerModel(" Walter Payton"),
                    AnswerModel(" Barry Sanders"),
                    AnswerModel(" Eric Dickerson", true),
                    AnswerModel(" Emmitt Smith"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "Who is the only player in NFL history to have won five rushing titles? ",
                answers = listOf(
                    AnswerModel(" Barry Sanders"),
                    AnswerModel(" Walter Payton"),
                    AnswerModel(" Emmitt Smith"),
                    AnswerModel(" Jim Brown", true),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "Who is the only player in NFL history to have won three Super Bowl MVP awards?  ",
                answers = listOf(
                    AnswerModel(" Tom Brady"),
                    AnswerModel(" Terry Bradshaw"),
                    AnswerModel(" Joe Montana", true),
                    AnswerModel(" Bart Starr"),
                ),
            ),
            QuestionsEntity(
                type = TYPE_USA_FOOTBALL,
                title = "Who is the only player in NFL history to have won two Super Bowl MVP awards as a defensive player?  ",
                answers = listOf(
                    AnswerModel(" Tom Ray Lewis", true),
                    AnswerModel(" Lawrence Taylor"),
                    AnswerModel(" Reggie White"),
                    AnswerModel(" Chuck Howley"),
                ),
            ),


            )


    override suspend fun getQuestionsByLevelId(id: Int): List<QuestionModel> {
        val entity = levelsDAo.getLevelById(id)
        try {
            when (entity.type) {
                TYPE_FOOTBALL -> {
                    val list = questionsDao.getAllFootball().reversed()
                    if (entity.name == "Low") {
                        return list.subList(15, 25).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    } else if (entity.name == "Medium") {
                        return list.subList(5, 15).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    } else {
                        return list.subList(0, 5).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    }
                }

                TYPE_BASKETBALL -> {
                    val list = questionsDao.getAllBasketBall().reversed()
                    if (entity.name == "Low") {
                        return list.subList(15, 25).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    } else if (entity.name == "Medium") {
                        return list.subList(5, 15).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    } else {
                        return list.subList(0, 5).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    }
                }

                TYPE_HOCKEY -> {
                    val list = questionsDao.getAllHockey().reversed()
                    if (entity.name == "Low") {
                        return list.subList(15, 25).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    } else if (entity.name == "Medium") {
                        return list.subList(5, 15).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    } else {
                        return list.subList(0, 5).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    }
                }

                TYPE_USA_FOOTBALL -> {
                    val list = questionsDao.getAllUsaFootbal().reversed()
                    if (entity.name == "Low") {
                        return list.subList(15, 25).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    } else if (entity.name == "Medium") {
                        return list.subList(5, 15).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    } else {
                        return list.subList(0, 5).map {
                            QuestionModel(it.id, it.type, it.title, it.answers)
                        }
                    }
                }

                else -> {
                    return listOf()
                }
            }
        } catch (e: Exception) {
            Log.d("tag", "$e")
            return listOf()
        }


    }

    override suspend fun answerTrue(levelId: Int) {
        levelsDAo.answerTrue(levelId)
        val level = levelsDAo.getLevelById(levelId)
        val counter = level.countCompleted
        val status = if (counter == 10) STATUS_COMPLETED else STATUS_STARTED
        if (status == STATUS_COMPLETED) {
            levelsDAo.updateLevel(levelsDAo.getLevelById(levelId - 1).copy(status = STATUS_STARTED))
        }
        levelsDAo.updateLevel(level.copy(status = status))
    }

}