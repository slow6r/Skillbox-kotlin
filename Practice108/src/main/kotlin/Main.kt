package main

import battle.Battle
import battle.BattleState
import battle.Team
import java.util.*

fun main() {
    println("Введите количество воинов в команде:")
    val teamSize = readLine()?.toIntOrNull() ?: 5

    val team1 = Team(teamSize)
    val team2 = Team(teamSize)
    val battle = Battle(team1, team2)

    println("\nНачало сражения!")
    println("Команда 1: $teamSize воинов")
    println("Команда 2: $teamSize воинов")
    println("Сражение начинается...\n")

    var iteration = 1
    while (true) {
        battle.performIteration()
        val state = battle.getBattleState()

        println("\nХод $iteration:")
        when (state) {
            is BattleState.Progress -> {
                println("Команда 1:")
                println("- Количество живых воинов: ${state.team1Alive}")
                println("- Общее здоровье: ${state.team1Health}")
                println("Команда 2:")
                println("- Количество живых воинов: ${state.team2Alive}")
                println("- Общее здоровье: ${state.team2Health}")
            }
            is BattleState.Team1Win -> {
                println("\nСражение завершено!")
                println("Победила команда 1!")
                println("Количество ходов: $iteration")
                break
            }
            is BattleState.Team2Win -> {
                println("\nСражение завершено!")
                println("Победила команда 2!")
                println("Количество ходов: $iteration")
                break
            }
            is BattleState.Draw -> {
                println("\nСражение завершено!")
                println("Ничья!")
                println("Количество ходов: $iteration")
                break
            }
        }

        iteration++
        Thread.sleep(1000)
    }
}