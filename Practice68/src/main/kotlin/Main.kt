import kotlin.random.Random

object Channels {
    internal val allChannels = listOf(
        "Первый канал", "Россия 1", "Матч ТВ", "НТВ", "Пятый канал",
        "Культура", "ТНТ", "СТС", "Домашний", "Карусель",
        "ТВ Центр", "РЕН ТВ", "Спас", "Звезда", "Мир",
        "Discovery", "National Geographic", "CNN", "BBC", "Euronews"
    )

    fun getRandomChannels(count: Int): List<String> {
        require(count <= allChannels.size) { "Запрошено каналов больше, чем доступно" }
        return allChannels.shuffled().take(count)
    }
}

class TV(
    val brand: String,
    val model: String,
    val diagonalSize: Int,
    channelCount: Int
) {
    private val channels: List<String>
    private var isPoweredOn: Boolean = false
    private var currentChannelIndex: Int = 0
    private var currentVolume: Int = 10

    init {
        channels = Channels.getRandomChannels(channelCount.coerceAtMost(Channels.allChannels.size))
    }

    fun togglePower() {
        isPoweredOn = !isPoweredOn
        println("$brand $model: ${if (isPoweredOn) "Включен" else "Выключен"}")
    }

    fun increaseVolume(amount: Int) {
        if (!isPoweredOn) {
            println("$brand $model: Телевизор выключен. Громкость не изменена.")
            return
        }
        currentVolume = (currentVolume + amount).coerceAtMost(MAX_VOLUME)
        println("$brand $model: Громкость увеличена до $currentVolume")
    }

    fun decreaseVolume(amount: Int) {
        if (!isPoweredOn) {
            println("$brand $model: Телевизор выключен. Громкость не изменена.")
            return
        }
        currentVolume = (currentVolume - amount).coerceAtLeast(0)
        println("$brand $model: Громкость уменьшена до $currentVolume")
    }

    fun switchChannel(channelNumber: Int) {
        if (!isPoweredOn) {
            isPoweredOn = true
            println("$brand $model: Телевизор включен.")
        }
        val index = (channelNumber - 1).mod(channels.size)
        currentChannelIndex = index
        println("$brand $model: Переключен на канал ${index + 1} (${channels[index]})")
    }

    fun channelUp() {
        if (!isPoweredOn) {
            isPoweredOn = true
            println("$brand $model: Телевизор включен. Текущий канал: ${channels[currentChannelIndex]}")
            return
        }
        currentChannelIndex = (currentChannelIndex + 1) % channels.size
        println("$brand $model: Канал ↑ ${currentChannelIndex + 1} (${channels[currentChannelIndex]})")
    }

    fun channelDown() {
        if (!isPoweredOn) {
            isPoweredOn = true
            println("$brand $model: Телевизор включен. Текущий канал: ${channels[currentChannelIndex]}")
            return
        }
        currentChannelIndex = (currentChannelIndex - 1 + channels.size) % channels.size
        println("$brand $model: Канал ↓ ${currentChannelIndex + 1} (${channels[currentChannelIndex]})")
    }

    fun getFormattedChannels(): String {
        return channels.mapIndexed { index, channel -> "${index + 1}. $channel" }.joinToString("\n")
    }

    companion object {
        const val MAX_VOLUME = 100
    }
}

fun main() {
    val tvs = listOf(
        TV("Sony", "Bravia", 50, 5),
        TV("LG", "OLED", 55, 7),
        TV("Samsung", "QLED", 60, 10)
    )

    println("=== Информация о телевизорах ===")
    tvs.forEachIndexed { index, tv ->
        println("Телевизор ${index + 1}: ${tv.brand} ${tv.model}, ${tv.diagonalSize}\"")
    }
    println()

    tvs.forEach { tv ->
        println("\n=== Тестирование ${tv.brand} ${tv.model} ===")
        tv.togglePower()

        // Проверка переключения каналов
        val randomChannel = Random.nextInt(1, 15)
        tv.switchChannel(randomChannel)

        // Проверка громкости
        repeat(3) { tv.increaseVolume(30) }
        repeat(2) { tv.decreaseVolume(50) }

        // Проверка переключения каналов
        repeat(3) { tv.channelUp() }
        repeat(3) { tv.channelDown() }

        // Вывод списка каналов
        println("\nСписок каналов:")
        println(tv.getFormattedChannels())

        // Проверка выключенного состояния
        tv.togglePower()
        tv.increaseVolume(20)
        tv.switchChannel(2)
        tv.channelUp()

        // Включение обратно для демонстрации
        tv.togglePower()
        println()
    }

    // Демонстрация циклического переключения каналов
    val demoTV = tvs.first()
    println("\n=== Демонстрация циклического переключения каналов ===")
    repeat(5) { demoTV.channelUp() }
    repeat(5) { demoTV.channelDown() }
}