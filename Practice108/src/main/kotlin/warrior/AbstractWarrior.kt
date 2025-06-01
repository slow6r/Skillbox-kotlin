package warrior

import utils.chance
import weapon.AbstractWeapon

abstract class AbstractWarrior(
    private val maxHealth: Int,
    override val dodgeChance: Int,
    private val accuracy: Int,
    private val weapon: AbstractWeapon
) : Warrior {
    var currentHealth: Int = maxHealth
        protected set

    override val isKilled: Boolean
        get() = currentHealth <= 0

    override fun attack(opponent: Warrior) {
        if (!weapon.hasAmmo) {
            weapon.reload()
            return
        }

        val ammoList = weapon.getAmmoForShot()
        var totalDamage = 0.0

        for (ammo in ammoList) {
            if (accuracy.chance() && !opponent.dodgeChance.chance()) {
                totalDamage += ammo.getDamage()
            }
        }

        if (totalDamage > 0) {
            opponent.takeDamage(totalDamage)
        }
    }

    override fun takeDamage(damage: Double) {
        currentHealth -= damage.toInt()
        if (currentHealth < 0) currentHealth = 0
    }
}