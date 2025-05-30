// Интерфейс конвертера валют
interface CurrencyConverter {
    val currencyCode: String
    fun convertRub(rubles: Double): Double
}

// Реализации конвертеров для конкретных валют
object UsdConverter : CurrencyConverter {
    override val currencyCode = "USD"
    override fun convertRub(rubles: Double): Double = rubles / 90.0 // Примерный курс
}

object EurConverter : CurrencyConverter {
    override val currencyCode = "EUR"
    override fun convertRub(rubles: Double): Double = rubles / 100.0 // Примерный курс
}

object GbpConverter : CurrencyConverter {
    override val currencyCode = "GBP"
    override fun convertRub(rubles: Double): Double = rubles / 115.0 // Примерный курс
}

// Объект-синглтон, хранящий все конвертеры
object Converters {
    private val converters = mapOf(
        UsdConverter.currencyCode to UsdConverter,
        EurConverter.currencyCode to EurConverter,
        GbpConverter.currencyCode to GbpConverter
    )

    fun get(currencyCode: String): CurrencyConverter {
        return converters[currencyCode] ?: createAnonymousConverter(currencyCode)
    }

    private fun createAnonymousConverter(currencyCode: String): CurrencyConverter {
        return object : CurrencyConverter {
            override val currencyCode = currencyCode
            override fun convertRub(rubles: Double): Double {
                println("Внимание: используется анонимный конвертер для неизвестной валюты $currencyCode!")
                return rubles / 50.0 // Произвольный курс для неизвестных валют
            }
        }
    }
}

fun main() {
    println("Конвертер валют")
    println("Доступные валюты: USD, EUR, GBP")
    println("Введите сумму в рублях и код валюты через пробел (например: 1000 USD)")

    val input = readlnOrNull()
    val parts = input?.split(" ") ?: listOf()

    if (parts.size != 2) {
        println("Ошибка ввода. Пожалуйста, введите сумму и код валюты через пробел.")
        return
    }

    try {
        val rubles = parts[0].toDouble()
        val currencyCode = parts[1].uppercase()

        val converter = Converters.get(currencyCode)
        val convertedAmount = converter.convertRub(rubles)

        println("\n${"%.2f".format(rubles)} рублей = ${"%.2f".format(convertedAmount)} ${converter.currencyCode}")
    } catch (e: NumberFormatException) {
        println("Ошибка: сумма должна быть числом")
    }
}