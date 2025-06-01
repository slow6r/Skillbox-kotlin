package warrior

interface Warrior {
    val isKilled: Boolean
    val dodgeChance: Int

    fun attack(opponent: Warrior)
    fun takeDamage(damage: Double)
}