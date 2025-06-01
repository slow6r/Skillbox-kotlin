package weapon

sealed class FireType {
    object SingleShot : FireType()
    data class BurstFire(val burstSize: Int) : FireType()
}