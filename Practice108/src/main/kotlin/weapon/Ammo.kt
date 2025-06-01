package weapon

import utils.chance

enum class Ammo(
    private val damage: Int,
    private val criticalChance: Int,
    private val criticalCoefficient: Double
) {
    BULLET(10, 20, 1.5),
    SHELL(25, 15, 2.0),
    ROCKET(50, 10, 3.0);

    fun getDamage(): Double {
        return if (criticalChance.chance()) {
            damage * criticalCoefficient
        } else {
            damage.toDouble()
        }
    }
}