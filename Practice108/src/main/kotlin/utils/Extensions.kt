package utils

import kotlin.random.Random

fun Int.chance(): Boolean = Random.nextInt(100) < this