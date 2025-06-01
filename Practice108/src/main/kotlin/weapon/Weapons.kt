package weapon

object Weapons {
    fun createPistol(): AbstractWeapon = object : AbstractWeapon(10, FireType.SingleShot, Ammo.BULLET) {
        override fun createAmmo(): Ammo = Ammo.BULLET
    }

    fun createRifle(): AbstractWeapon = object : AbstractWeapon(30, FireType.BurstFire(3), Ammo.BULLET) {
        override fun createAmmo(): Ammo = Ammo.BULLET
    }

    fun createShotgun(): AbstractWeapon = object : AbstractWeapon(8, FireType.SingleShot, Ammo.SHELL) {
        override fun createAmmo(): Ammo = Ammo.SHELL
    }

    fun createRocketLauncher(): AbstractWeapon = object : AbstractWeapon(2, FireType.SingleShot, Ammo.ROCKET) {
        override fun createAmmo(): Ammo = Ammo.ROCKET
    }
}