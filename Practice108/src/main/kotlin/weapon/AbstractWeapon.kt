package weapon

import stack.Stack
import utils.chance

abstract class AbstractWeapon(
    private val maxAmmo: Int,
    private val fireType: FireType,
    private val ammoType: Ammo
) {
    private var ammoStack: Stack<Ammo> = Stack()

    val hasAmmo: Boolean
        get() = !ammoStack.isEmpty()

    fun reload() {
        ammoStack = Stack()
        repeat(maxAmmo) {
            ammoStack.push(createAmmo())
        }
    }

    protected abstract fun createAmmo(): Ammo

    fun getAmmoForShot(): List<Ammo> {
        return when (fireType) {
            is FireType.SingleShot -> listOfNotNull(ammoStack.pop())
            is FireType.BurstFire -> {
                val burstSize = fireType.burstSize
                List(burstSize) { ammoStack.pop() }.filterNotNull()
            }
        }
    }

    init {
        reload()
    }
}