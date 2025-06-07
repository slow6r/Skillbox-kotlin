import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

data class Card(val rows: List<List<Int?>>, val marked: Array<Array<Boolean>>) {
    companion object {
        fun generate(): Card {
            val numbers = (1..90).toMutableList()
            val rows = List(3) {
                List(9) { index ->
                    if (index < 5) numbers.removeAt(Random.nextInt(numbers.size)) else null
                }
            }
            return Card(rows, Array(3) { Array(9) { false } })
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        if (rows != other.rows) return false
        if (!marked.contentDeepEquals(other.marked)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rows.hashCode()
        result = 31 * result + marked.contentDeepHashCode()
        return result
    }
}

class Player(val name: String, val cards: List<Card>) {
    fun markNumber(number: Int): Boolean {
        var markedAny = false
        cards.forEach { card ->
            card.rows.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, value ->
                    if (value == number) {
                        card.marked[rowIndex][colIndex] = true
                        println("$name отметил число $number на карточке")
                        markedAny = true
                        if (card.marked[rowIndex].all { it }) {
                            return true
                        }
                    }
                }
            }
        }
        if (!markedAny) {
            println("$name не отметил число $number")
        }
        return false
    }
}

class Host {
    private val _barrelFlow = MutableSharedFlow<Int>()
    val barrelFlow: SharedFlow<Int> = _barrelFlow.asSharedFlow()
    private val barrels = (1..90).toMutableList()

    init {
        barrels.shuffle()
    }

    suspend fun start() {
        barrels.forEach { barrel ->
            println("Ведущий достал бочонок: $barrel")
            _barrelFlow.emit(barrel)
            delay(50)
        }
    }

    fun stop() {
        // Остановка генерации
    }
}

fun main() = runBlocking {
    val host = Host()
    val players = listOf(
        Player("Игрок 1", listOf(Card.generate())),
        Player("Игрок 2", listOf(Card.generate()))
    )

    val job = launch {
        host.barrelFlow.collect { number ->
            players.forEach { player ->
                if (player.markNumber(number)) {
                    println("${player.name} выиграл!")
                    host.stop()
                    cancel()
                }
            }
        }
    }

    host.start()
    job.join()
} 