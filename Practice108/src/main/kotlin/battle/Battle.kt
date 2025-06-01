package battle

import warrior.AbstractWarrior
import warrior.Warrior

class Battle(private val team1: Team, private val team2: Team) {
    private var battleOver = false

    fun getBattleState(): BattleState {
        return when {
            team1.isDefeated && team2.isDefeated -> BattleState.Draw
            team1.isDefeated -> BattleState.Team2Win
            team2.isDefeated -> BattleState.Team1Win
            else -> {
                val team1Health = team1.warriors.sumOf { warrior -> 
                    if (warrior is AbstractWarrior) warrior.currentHealth else 0 
                }
                val team2Health = team2.warriors.sumOf { warrior -> 
                    if (warrior is AbstractWarrior) warrior.currentHealth else 0 
                }
                BattleState.Progress(
                    team1Health,
                    team1.warriors.count { !it.isKilled },
                    team2Health,
                    team2.warriors.count { !it.isKilled }
                )
            }
        }
    }

    fun performIteration() {
        if (battleOver) return

        val aliveTeam1 = team1.warriors.filter { !it.isKilled }.shuffled()
        val aliveTeam2 = team2.warriors.filter { !it.isKilled }.shuffled()

        aliveTeam1.zip(aliveTeam2).forEach { (warrior1, warrior2) ->
            warrior1.attack(warrior2)
            warrior2.attack(warrior1)
        }

        battleOver = getBattleState() !is BattleState.Progress
    }
}