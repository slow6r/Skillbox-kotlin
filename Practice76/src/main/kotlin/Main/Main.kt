import kotlin.random.Random

open class Animal(
    internal var energy: Int,
    internal var weight: Int,
    internal val maxAge: Int,
    val name: String
) {
    var currentAge: Int = 0
        private set

    val isTooOld: Boolean
        get() = currentAge >= maxAge

    private fun tryIncrementAge() {
        if (Random.nextBoolean()) {
            currentAge++
        }
    }

    open fun sleep() {
        if (isTooOld) return
        energy += 5
        currentAge++
        println("$name спит")
    }

    open fun eat() {
        if (isTooOld) return
        energy += 3
        weight += 1
        tryIncrementAge()
        println("$name ест")
    }

    open fun move(): Boolean {
        if (isTooOld || energy < 5 || weight < 1) return false
        energy -= 5
        weight -= 1
        tryIncrementAge()
        println("$name передвигается")
        return true
    }

    open fun reproduce(): Animal? {
        if (isTooOld) return null
        val childEnergy = (1..10).random()
        val childWeight = (1..5).random()
        val child = Animal(childEnergy, childWeight, maxAge, name)
        println("Рождено животное: ${child.name}, энергия: ${child.energy}, вес: ${child.weight}, возраст: ${child.currentAge}, макс. возраст: ${child.maxAge}")
        return child
    }
}

class Bird(
    energy: Int,
    weight: Int,
    maxAge: Int,
    name: String = "Птица"
) : Animal(energy, weight, maxAge, name) {

    override fun move(): Boolean {
        val moved = super.move()
        if (moved) {
            println("$name летит")
        }
        return moved
    }

    override fun reproduce(): Animal? {
        val child = super.reproduce() ?: return null
        return Bird(child.energy, child.weight, maxAge, name)
    }
}

class Fish(
    energy: Int,
    weight: Int,
    maxAge: Int,
    name: String = "Рыба"
) : Animal(energy, weight, maxAge, name) {

    override fun move(): Boolean {
        val moved = super.move()
        if (moved) {
            println("$name плывет")
        }
        return moved
    }

    override fun reproduce(): Animal? {
        val child = super.reproduce() ?: return null
        return Fish(child.energy, child.weight, maxAge, name)
    }
}

class Dog(
    energy: Int,
    weight: Int,
    maxAge: Int,
    name: String = "Собака"
) : Animal(energy, weight, maxAge, name) {

    override fun move(): Boolean {
        val moved = super.move()
        if (moved) {
            println("$name бежит")
        }
        return moved
    }

    override fun reproduce(): Animal? {
        val child = super.reproduce() ?: return null
        return Dog(child.energy, child.weight, maxAge, name)
    }
}

class NatureReserve {
    val animals = mutableListOf<Animal>()

    init {
        repeat(5) {
            animals.add(Bird((1..10).random(), (1..5).random(), 10, "Птица ${it + 1}"))
        }
        repeat(3) {
            animals.add(Fish((1..10).random(), (1..5).random(), 8, "Рыба ${it + 1}"))
        }
        repeat(2) {
            animals.add(Dog((1..10).random(), (1..5).random(), 15, "Собака ${it + 1}"))
        }
        repeat(3) {
            animals.add(Animal((1..10).random(), (1..5).random(), 12, "Животное ${it + 1}"))
        }
    }
}

fun main() {
    val reserve = NatureReserve()
    val n = 10

    for (iteration in 1..n) {
        println("\nИтерация $iteration")

        val newAnimals = mutableListOf<Animal>()
        reserve.animals.forEach { animal ->
            if (animal.isTooOld) return@forEach

            when ((0..3).random()) {
                0 -> animal.sleep()
                1 -> animal.eat()
                2 -> animal.move()
                3 -> animal.reproduce()?.let { newAnimals.add(it) }
            }
        }
        reserve.animals.addAll(newAnimals)
        reserve.animals.removeAll { it.isTooOld }

        if (reserve.animals.isEmpty()) {
            println("Все животные исчезли.")
            return
        }
    }

    println("\nОсталось животных: ${reserve.animals.size}")
}