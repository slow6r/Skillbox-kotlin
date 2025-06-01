package battle

import warrior.*
import utils.chance

class Team(private val teamSize: Int) {
    val warriors: List<Warrior> = List(teamSize) { createRandomWarrior() }

    private fun createRandomWarrior(): Warrior {
        return when {
            (10).chance() -> General()
            (40).chance() -> Captain()
            else -> Soldier()
        }
    }

    val isDefeated: Boolean
        get() = warriors.all { it.isKilled }
}